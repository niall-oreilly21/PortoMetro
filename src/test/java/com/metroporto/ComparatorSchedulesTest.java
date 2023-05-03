package com.metroporto;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparatorSchedulesTest
{
    private ComparatorSchedules comparatorSchedules;
    private StationDaoInterface stationDao;
    private Station stationOne;
    private Station stationTwo;


    @BeforeEach
    void setUp() throws DaoException
    {
        stationDao = new MySqlStationDao();
        stationOne = stationDao.findStationByStationId("PLU");
        stationTwo = stationDao.findStationByStationId("FNZ");

        Set<Station> stations = new HashSet();
        stations.add(stationOne);
        stations.add(stationTwo);

        comparatorSchedules = new ComparatorSchedules(stations);
        stationDao = new MySqlStationDao();
    }

    @Test
    void ComparatorSchedulesEqualsMinusOne()
    {
        System.out.println("\nComparatorSchedulesClosestTime returns 1 when scheduleOne is greater than scheduleTwo");
        Schedule scheduleOne = new Schedule(stationTwo, LocalTime.of(15,22));
        Schedule scheduleTwo = new Schedule(stationOne, LocalTime.of(14,22));

        int expected = 1;
        int actual = comparatorSchedules.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }

    @Test
    void ComparatorSchedulesEqualsOne()
    {
        System.out.println("\nComparatorSchedules returns -1 when scheduleOne is less than scheduleTwo");
        Schedule scheduleOne = new Schedule(stationOne, LocalTime.of(16,22));
        Schedule scheduleTwo = new Schedule(stationTwo, LocalTime.of(15,22));

        int expected = -1;
        int actual = comparatorSchedules.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }

    @Test
    void ComparatorSchedulesEqualsZero()
    {
        System.out.println("\nComparatorSchedulesClosestTime returns 0 when the scheduleOne is is less than scheduleTwo");
        Schedule scheduleOne = new Schedule(stationOne, LocalTime.of(15,22));
        Schedule scheduleTwo = new Schedule(stationOne, LocalTime.of(15,22));

        int expected = 0;
        int actual = comparatorSchedules.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }
}
