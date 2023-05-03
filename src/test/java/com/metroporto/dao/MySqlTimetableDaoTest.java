package com.metroporto.dao;

import com.metroporto.dao.timetabledao.MySqlTimetableDao;
import com.metroporto.dao.timetabledao.TimetableDaoInterface;
import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Timetable;
import com.metroporto.metro.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MySqlTimetableDaoTest
{
    private TimetableDaoInterface timetableDao;

    @BeforeEach
    void setUp()
    {
        timetableDao = new MySqlTimetableDao();
    }

    @Test
    void findAllTimetablesByRouteIdAssertFalse() throws DaoException
    {
        System.out.println("\nfindAllTimetablesByRouteId() does not return a empty List for route id 1.");
        List<Timetable> timetables = timetableDao.findAllTimetablesByRouteId(1);
        assertFalse(timetables.isEmpty());
    }

    @Test
    void findAllTimetablesByRouteIdAssertTrue() throws DaoException
    {
        System.out.println("\nfindAllTimetablesByRouteId() returns a empty List for route id -1.");
        List<Timetable> timetables = timetableDao.findAllTimetablesByRouteId(-1);
        assertTrue(timetables.isEmpty());
    }
}
