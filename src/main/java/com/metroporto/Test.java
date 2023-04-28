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
        Station startStation = stationDao.findStationByStationId("IPO");
        Station endStation = stationDao.findStationByStationId("SEB");


        JourneyPlanner journeyPlanner = new JourneyPlanner(startStation, endStation, LocalTime.of(16,19), TimeTableType.SATURDAY);

        MetroSystem metroSystem = new MetroSystem(lines);
        journeyPlanner.setMetroSystem(metroSystem);
        journeyPlanner.start();
        journeyPlanner.displayJourneyPlanner();

    }
}
