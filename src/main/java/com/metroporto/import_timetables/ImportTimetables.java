package com.metroporto.import_timetables;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.scheduledao.MySqlScheduleDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.timetabledao.MySqlTimetableDao;
import com.metroporto.dao.timetabledao.TimetableDaoInterface;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;

import java.util.List;

//Run this class to insert Timetables to database

public class ImportTimetables implements ImportTimetablesInterface
{
    private static List<Station>stations;
    private static List<Line>lines;

    public static void main(String[] args)
    {
        ImportTimetables importTimetables = new ImportTimetables();
        importTimetables.start();
    }

    @Override
    public void start()
    {
        setUpStationsLines();

        retrieveTimetables();

       insertSchedulesTimetablesToDatabase();

       //Used to test
        displayTimetables();
    }

    private void setUpStationsLines()
    {
        StationDaoInterface stationDao = new MySqlStationDao();
        LineDaoInterface lineDao = new MySqlLineDao();

        try
        {
            stations = stationDao.findAll();
            lines = lineDao.findAll();

        } catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }

    private void retrieveTimetables()
    {
        PdfReaderTimetables pdfReaderTimetables  = new PdfReaderTimetables(stations, lines);
        pdfReaderTimetables.start();

        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations, lines);
        excelReaderTimetables.start();
    }

    private void displayTimetables()
    {
        for (Line line : lines)
        {
            if (line.getLineId().equalsIgnoreCase("D"))
            {
                System.out.println("------" + line.getLineName() + "-----");

                for (Route route : line.getRoutes())
                {
                    System.out.println("------" + route.getEndStation().getStationName() + "-----");
                    for (Timetable timetable : route.getTimetables())
                    {
                        if(timetable.getTimeTableType().equals(TimeTableType.MONDAY_TO_FRIDAY))
                        {
                            timetable.displayTimeTable();
                            System.out.println(timetable.getTimetableSchedules().size());
                        }

                    }
                }
            }

        }
    }

    private void insertSchedulesTimetablesToDatabase()
    {
        TimetableDaoInterface mySqlTimetableDao = new MySqlTimetableDao();
        MySqlScheduleDao mySqlScheduleDao = new MySqlScheduleDao();

        int i;

        try
        {
            for (Line line : lines)
            {
                for (Route route : line.getRoutes())
                {
                    for (Timetable timetable : route.getTimetables())
                    {
                        mySqlTimetableDao.insertTimetableByRouteId(timetable, route.getRouteId());

                        i = 0;

                        for (List<Schedule> schedules : timetable.getTimetableSchedules())
                        {
                            i++;
                            mySqlScheduleDao.insertSchedulesByRow(schedules, timetable.getTimetableId(), i);
                        }
                    }
                }
            }

        } catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }
}
