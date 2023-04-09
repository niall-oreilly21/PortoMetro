package com.metroporto;

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

public class ExcelReader {
    public static void main(String[] args)
    {
        List<String> excelRegions = new ArrayList<>(Arrays.asList("Monday_Friday_1", "Saturday_1", "Sunday_1", "Monday-Friday_2", "Saturday_2", "Sunday_2"));

//        Route route1 = new Route(1,null);
//        Route route2 = new Route(2,null);

        List<Timetable>timetables = new ArrayList<>();

//        for(String region : excelRegions)
//        {
            timetables.add(getTimeTable(excelRegions.get(0)));

            timetables.get(0).displayTimeTable();
        //}

    }

    private static Timetable getTimeTable(String region)
    {
        Timetable timetable = new Timetable(1,"", TimeTableType.SUNDAY);

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

                for (int rowIndex = range.getFirstRow(); rowIndex <= range.getLastRow(); rowIndex++)
                {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null)
                    {
                        continue;
                    }

                    List<Schedule> rowSchedules = new ArrayList<>();

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
                                //System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case FORMULA:
                                switch (cell.getCachedFormulaResultType())
                                {
                                    case NUMERIC:
                                        rowSchedules.add(new Schedule(null, cell.getLocalDateTimeCellValue().toLocalTime()));
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
}
