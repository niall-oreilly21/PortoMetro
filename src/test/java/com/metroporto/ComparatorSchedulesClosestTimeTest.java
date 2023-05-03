package com.metroporto;

import com.metroporto.comparators.ComparatorSchedulesClosestTime;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparatorSchedulesClosestTimeTest
{
    private ComparatorSchedulesClosestTime comparatorSchedulesClosestTime;
    private StationDaoInterface stationDao;
    private Station stationOne;
    private Station stationTwo;


    @BeforeEach
    void setUp() throws DaoException
    {
        comparatorSchedulesClosestTime = new ComparatorSchedulesClosestTime(LocalTime.of(15, 00));
        stationDao = new MySqlStationDao();
        stationOne = stationDao.findStationByStationId("PLU");
        stationTwo = stationDao.findStationByStationId("FNZ");
    }


    @Test
    void ComparatorSchedulesClosestTimeEqualsOne()
    {
        System.out.println("\nComparatorSchedulesClosestTime returns 1 when scheduleOne is is not closer to scheduleTwo then to the start time");
        Schedule scheduleOne = new Schedule(stationOne, LocalTime.of(16,22));
        Schedule scheduleTwo = new Schedule(stationTwo, LocalTime.of(15,22));

        int expected = 1;
        int actual = comparatorSchedulesClosestTime.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }

    @Test
    void ComparatorSchedulesClosestTimeEqualsMinusOne()
    {
        System.out.println("\nComparatorSchedulesClosestTime returns -1 when scheduleOne is closer to scheduleTwo then to the start time");
        Schedule scheduleOne = new Schedule(stationOne, LocalTime.of(15,22));
        Schedule scheduleTwo = new Schedule(stationTwo, LocalTime.of(14,22));

        int expected = -1;
        int actual = comparatorSchedulesClosestTime.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }

    @Test
    void ComparatorSchedulesClosestTimeEqualsZero()
    {
        System.out.println("\nComparatorSchedulesClosestTime returns 0 when the scheduleOne is equal to the scheduleTwo");
        Schedule scheduleOne = new Schedule(stationOne, LocalTime.of(15,22));
        Schedule scheduleTwo = new Schedule(stationTwo, LocalTime.of(15,22));

        int expected = 0;
        int actual = comparatorSchedulesClosestTime.compare(scheduleOne, scheduleTwo);
        assertEquals(expected,actual);
    }
}
