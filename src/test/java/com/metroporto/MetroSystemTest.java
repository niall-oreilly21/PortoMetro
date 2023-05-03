package com.metroporto;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MetroSystemTest
{
    private StationDaoInterface stationDao;
    private LineDaoInterface linesDao;
    private MetroSystem metroSystem;

    @BeforeEach
    void setUp() throws DaoException
    {
        stationDao = new MySqlStationDao();
        linesDao = new MySqlLineDao();

        List<Line>lines = linesDao.findAll();
        metroSystem = new MetroSystem(lines);
    }

    @Test
    void getTotalJourneyDuration() throws DaoException
    {
        System.out.println("\ngetTotalJourneyDuration() returns the total length of a trip between Pólo Universitário and Fânzeres.");

        Station stationOne = stationDao.findStationByStationId("PLU");
        Station stationTwo = stationDao.findStationByStationId("FNZ");
        LocalTime startTime = LocalTime.of(14,40);


        JourneyPlanner journeyPlanner = new JourneyPlanner(stationOne, stationTwo, startTime, TimeTableType.SATURDAY);
        journeyPlanner.setMetroSystem(metroSystem);
        journeyPlanner.start();

        int expectedTime = 50;
        int actualTime = journeyPlanner.getTotalJourneyDuration();

        assertEquals(expectedTime, actualTime);
    }
}
