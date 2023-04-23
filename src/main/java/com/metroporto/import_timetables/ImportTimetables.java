package com.metroporto.import_timetables;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.scheduledao.MySqlScheduleDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.timetabledao.MySqlTimetableDao;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;

import java.util.List;

public class ImportTimetables implements ImportTimetablesInterface
{
    private static List<Station>stations;
    private static List<Line>lines;

    public static void main(String[] args)
    {
        StationDaoInterface stationDao = new MySqlStationDao();
        LineDaoInterface routeDao = new MySqlLineDao();
        UserDaoInterface userDao = new MySqlUserDao();
        try
        {
            stations = stationDao.findAllStations();
            lines = routeDao.findAllLines();

        } catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
        ImportTimetables importTimetables = new ImportTimetables();


        for (Line line : lines)
        {
            System.out.println("------" + line.getLineName() + "-----");

            for (Route route : line.getRoutes())
            {
                System.out.println("------" + route.getEndStation().getStationName() + "-----");
                for (Timetable timetable : route.getTimetables())
                {
                    timetable.displayTimeTable();
                }
            }

        }

        //importTimetables.start();
    }


        @Override
        public void start()
        {
            MySqlTimetableDao mySqlTimetableDao = new MySqlTimetableDao();
            MySqlScheduleDao mySqlScheduleDao = new MySqlScheduleDao();

        PdfReaderTimetables pdfReaderTimetables  = new PdfReaderTimetables(stations, lines);
        pdfReaderTimetables.start();

            ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations, lines);
            excelReaderTimetables.start();

            int i;
            try
            {
                for (Line line : lines)
                {
                    for (Route route : line.getRoutes())
                    {

                        for (Timetable timetable : route.getTimetables())
                        {
                            mySqlTimetableDao.insert(timetable, route.getRouteId());

                            System.out.println(timetable.getTimetableId());

                            i = 1;
                            for (List<Schedule> schedules : timetable.getTimetableSchedules())
                            {
                                mySqlScheduleDao.insert(schedules, timetable.getTimetableId(), i);
                                i++;
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

