package com.metroporto.import_timetables;

import com.metroporto.enums.TimeTableType;
import com.metroporto.metro.Route;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import com.metroporto.metro.Timetable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.metroporto.enums.TimeTableType.*;

public class ExcelReaderTimetables implements ImportTimetablesInterface
{
    private List<Station> stations;

    public ExcelReaderTimetables(List<Station> stations)
    {
        this.stations = stations;
    }

    @Override
    public void start()
    {
        List<String> excelRegions = new ArrayList<>(Arrays.asList("Monday_Friday_1", "Saturday_1", "Sunday_1", "Monday_Friday_2", "Saturday_2", "Sunday_2"));

//        Route route1 = new Route(1,null);
//        Route route2 = new Route(2,null);

        List<Timetable>timetables = new ArrayList<>();

        for(String region : excelRegions)
        {
            timetables.add(getTimeTable(region));
        }

        for(Timetable timetable : timetables)
        {
            timetable.displayTimeTable();
            System.out.println();
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

                TimeTableType timeTableType = getTimeTableTypeFromRegion(rowOne.getCell(0).getStringCellValue());

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
                                switch (cell.getCachedFormulaResultType())
                                {
                                    case NUMERIC:
                                        if(i == stationsList.size())
                                        {
                                            i = 0;
                                        }
                                        rowSchedules.add(new Schedule(stationsList.get(i), cell.getLocalDateTimeCellValue().toLocalTime()));
                                        i++;
                                        break;
                                }
                                break;
                            default:
                                break;
                        }


                    }
                    timetable.addSchedules(rowSchedules);
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

    private TimeTableType getTimeTableTypeFromRegion(String stringCellValue)
    {
        TimeTableType timeTableType = MONDAY_TO_FRIDAY;

       if(stringCellValue.equalsIgnoreCase(MONDAY_TO_FRIDAY.getLabel()))
       {
           timeTableType = MONDAY_TO_FRIDAY;
       }
       else if(stringCellValue.equalsIgnoreCase(SATURDAY.getLabel()))
       {
           timeTableType = SATURDAY;
       }
       else
       {
           timeTableType = SUNDAY;
       }

        return timeTableType;
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
