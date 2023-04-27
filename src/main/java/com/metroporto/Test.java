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
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import com.metroporto.metro.University;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.time.LocalTime;
import java.util.*;

public class Test
{

    public static void main(String[] args) throws DaoException
    {
        LineDaoInterface lineDao = new MySqlLineDao();
        List<Line> lines = lineDao.findAll();

        StationDaoInterface stationDao = new MySqlStationDao();
        List<Station>daoStations = stationDao.findAll();
        Station startStation = stationDao.findStationByStationId("DRG");
        Station endStation = stationDao.findStationByStationId("IPO");


        Set<Station> stationSet = new LinkedHashSet<>();
        //System.out.println(line.getRoutes().get(0));

        int i;

        Line l = new Line(lines.get(0));

        List<Line>lines1 = new ArrayList<>();

        lines1.add(l);

//        if(lines1.contains(lines.get(0)))
//        {
//            System.out.println("jhjjjjjjh");
//        }


        for(Line line : lines)
        {
//            if(line.getLineId().equalsIgnoreCase("D"))
//            {
            //System.out.println(line.getLineName());
            Set<Station> stations = line.getRoutes().get(0).getTimetables().get(0).getStations();

            for (Station station : stations)
            {
                line.getStations().add(station);
            }
        //}
        }

        MetroSystem metroSystem = new MetroSystem(lines);


//        for (Station station : previousStations)
//        {
//            System.out.print(station.getStationName() + " -> ");
//        }

        metroSystem.findShortestPath(startStation, endStation, LocalTime.of(10,00), TimeTableType.MONDAY_TO_FRIDAY);

    }
}
