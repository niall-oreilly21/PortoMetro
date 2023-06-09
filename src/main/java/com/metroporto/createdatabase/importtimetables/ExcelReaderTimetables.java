package com.metroporto.createdatabase.importtimetables;

import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.enums.TimetableType;
import com.metroporto.metro.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelReaderTimetables extends ImportReaderLinesStations
{
    public ExcelReaderTimetables(List<Station> stations, List<Line> lines)
    {
        super(stations, lines);
    }

    @Override
    public void start()
    {
        List<String> excelRegions = new ArrayList<>(Arrays.asList("Monday_Friday_1", "Saturday_1", "Sunday_1", "Monday_Friday_2", "Saturday_2", "Sunday_2"));

        changeLine("D");

        int i = 0;
        for(String region : excelRegions)
        {
            if(region.contains("1"))
            {
                line.getRoutes().get(0).addTimeTable(getTimeTable(region));
            }
            else
            {
                line.getRoutes().get(1).addTimeTable(getTimeTable(region));
            }
            i++;
        }
    }

    private Timetable getTimeTable(String region)
    {
        Timetable timetable = new Timetable();

        try {
            // Create a workbook instance from the Excel file
            Workbook workbook = WorkbookFactory.create(new FileInputStream("lineD_timetables.xlsx"));

            // Get the first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
            Name namedRegion = workbook.getName(region);

            if (namedRegion != null)
            {
                // Get the formula for the named region
                String formula = namedRegion.getRefersToFormula();

                // Create a cell range address from the formula
                CellRangeAddress range = CellRangeAddress.valueOf(formula);

                List<Station>stationsList = new ArrayList<>();

                Row rowOne = sheet.getRow(range.getFirstRow());

                TimetableType timeTableType = getTimeTableTypeFromRegion(rowOne.getCell(0).getStringCellValue());

                timetable.setTimeTableType(timeTableType);

                for (int rowIndex = range.getFirstRow() + 1; rowIndex <= range.getLastRow(); rowIndex++)
                {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null)
                    {
                        continue;
                    }

                    List<Schedule> rowSchedules = new ArrayList<>();

                    int i = 0;
                    // Loop through the cells in the row
                    for (int columnIndex = range.getFirstColumn(); columnIndex <= range.getLastColumn(); columnIndex++)
                    {
                        Cell cell = row.getCell(columnIndex);
                        if (cell == null)
                        {
                            continue;
                        }

                        switch (cell.getCellType())
                        {
                            case STRING:
                                stationsList.add(getStation(cell.getStringCellValue()));
                                break;

                            case FORMULA:
                                if (cell.getCachedFormulaResultType() == CellType.NUMERIC)
                                {
                                    if (i == stationsList.size())
                                    {
                                        i = 0;
                                    }
                                    rowSchedules.add(new Schedule(stationsList.get(i), cell.getLocalDateTimeCellValue().toLocalTime()));
                                    i++;
                                }
                                break;
                            default:
                                break;
                        }


                    }
                    if (!rowSchedules.isEmpty())
                    {
                        timetable.addSchedules(rowSchedules);
                    }

                }
            }

            // Close the workbook
            workbook.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return timetable;
    }

    private TimetableType getTimeTableTypeFromRegion(String stringCellValue)
    {
        EnumLabelConverter enumLabelConverter = new EnumLabelConverter();
        return enumLabelConverter.fromLabel(stringCellValue, TimetableType.class);
    }

    private Station getStation(String stationString)
    {
        Station newStation = null;
        for (Station station : stations)
        {
            if (stationString.equalsIgnoreCase(station.getStationName()))
            {
                newStation = station;
            }
        }
        return newStation;
    }

}
