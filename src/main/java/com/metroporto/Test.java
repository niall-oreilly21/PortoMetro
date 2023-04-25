package com.metroporto;

import com.metroporto.cards.Card;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import com.metroporto.metro.University;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.util.*;

public class Test
{

    public static void main(String[] args) throws DaoException
    {
        LineDaoInterface lineDao = new MySqlLineDao();
        List<Line> lines = lineDao.findAll();

        StationDaoInterface stationDao = new MySqlStationDao();
        List<Station>daoStations = stationDao.findAll();
        Station startStation = stationDao.findStationByStationId("HSJ");
        Station endStation = stationDao.findStationByStationId("FNZ");


        Set<Station> stationSet = new LinkedHashSet<>();
        //System.out.println(line.getRoutes().get(0));

        int i;
        for(Line line : lines)
        {
//            if(line.getLineId().equalsIgnoreCase("C"))
//            {
                //System.out.println(line.getLineName());
                List<Station> stations = line.getRoutes().get(0).getTimetables().get(0).getStations();
                line.setStations(stations);

                i = 0;
                for(Station station : stations)
                {
                    i++;
                    //System.out.println(i + " " + station.getStationName());
                    stationSet.add(station);
                }
        }

//        System.out.println("DAO " + daoStations.size());
//
//        System.out.println("SET " + stationSet.size());

        //System.out.println(i);


//        for (Station station : daoStations)
//        {
//            if (!stationSet.contains(station))
//            {
//                System.out.println(station.getStationName());
//            }
//        }



        MetroSystem metroSystem = new MetroSystem(lines);


        List<Station>previousStations = metroSystem.findShortestPath(startStation, endStation);

        for (Station station : previousStations)
        {
            System.out.print(station.getStationName() + " -> ");
        }

    }
}
