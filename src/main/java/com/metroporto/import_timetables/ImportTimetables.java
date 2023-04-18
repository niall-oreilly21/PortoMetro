package com.metroporto.import_timetables;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.util.ArrayList;
import java.util.List;

public class ImportTimetables implements ImportTimetablesInterface
{
    private static List<Station>stations;

    public static void main(String[] args)
    {
        StationDaoInterface stationDao = new MySqlStationDao();

        try
        {
            stations = stationDao.findAllStations();
        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }

        ImportTimetables importTimetables = new ImportTimetables();

        importTimetables.start();
    }

    @Override
    public void start()
    {
//        PdfReaderTimetables pdfReaderTimetables = new PdfReaderTimetables(createStations());
//        pdfReaderTimetables.start();

        ExcelReaderTimetables excelReaderTimetables = new ExcelReaderTimetables(stations);
        excelReaderTimetables.start();
    }
}

