package com.metroporto.import_timetables;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;
import com.metroporto.users.User;
import java.util.List;

public class ImportTimetables implements ImportTimetablesInterface
{
    private static List<Station>stations;
    private static List<Line>lines;
    private static List<User>users;

    public static void main(String[] args)
    {
        StationDaoInterface stationDao = new MySqlStationDao();
        LineDaoInterface routeDao = new MySqlLineDao();
        UserDaoInterface userDao = new MySqlUserDao();
        try
        {
            stations = stationDao.findAllStations();
            lines = routeDao.findAllLines();
            users = userDao.findAllUsers();

        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }

        for(User user : users)
        {
            System.out.println(user);
        }

        ImportTimetables importTimetables = new ImportTimetables();

        importTimetables.start();
    }

    @Override
    public void start()
    {
//        PdfReaderTimetables pdfReaderTimetables = new PdfReaderTimetables(stations, lines);
//        pdfReaderTimetables.start();

        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations, lines);
        excelReaderTimetables.start();

        for (Line line : lines)
        {
            System.out.println("-----------" + line.getLineName() + "-------------");
            for (Route route : line.getRoutes())
            {
                for (Timetable timetable : route.getTimetables())
                {
                    timetable.displayTimeTable();
                    System.out.println();
                }
            }
        }
    }

}

