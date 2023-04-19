package com.metroporto.import_timetables;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.routedao.RouteDaoInterface;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;
import com.metroporto.users.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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

        //importTimetables.start();
    }

    @Override
    public void start()
    {
//        PdfReaderTimetables pdfReaderTimetables = new PdfReaderTimetables(stations);
//        pdfReaderTimetables.start();
//
//        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations);
//        excelReaderTimetables.start();
    }

}

