package com.metroporto.import_timetables;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.routedao.RouteDaoInterface;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImportTimetables implements ImportTimetablesInterface
{
    private static List<Station>stations;
    private static List<Line>lines;

    public static void main(String[] args)
    {
        StationDaoInterface stationDao = new MySqlStationDao();
        LineDaoInterface routeDao = new MySqlLineDao();
        try
        {
            stations = stationDao.findAllStations();
            lines = routeDao.findAllLines();
        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }

        for(Line line : lines)
        {
            System.out.println(line);
        }

        ImportTimetables importTimetables = new ImportTimetables();

        //importTimetables.start();
    }

    @Override
    public void start()
    {
        PdfReaderTimetables pdfReaderTimetables = new PdfReaderTimetables(stations);
        pdfReaderTimetables.start();

        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations);
        excelReaderTimetables.start();
    }


}

