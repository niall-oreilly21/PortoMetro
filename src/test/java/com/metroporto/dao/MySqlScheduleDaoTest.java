package com.metroporto.dao;

import com.metroporto.dao.scheduledao.MySqlScheduleDao;
import com.metroporto.dao.scheduledao.ScheduleDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MySqlScheduleDaoTest
{

    private ScheduleDaoInterface scheduleDao;

    @BeforeEach
    void setUp()
    {
        scheduleDao = new MySqlScheduleDao();
    }

    @Test
    void findAllSchedulesByTimetableIdAssertFalse() throws DaoException
    {
        System.out.println("\nfindAllSchedulesByTimetableId() does returns a empty List for timetable Id 1.");
        List<List<Schedule>>schedules = scheduleDao.findAllSchedulesByTimetableId(1);
        assertFalse(schedules.isEmpty());
    }

    @Test
    void findAllSchedulesByTimetableIdAssertTrue() throws DaoException
    {
        System.out.println("\nfindAllSchedulesByTimetableId() returns a empty List for timetable Id -1.");
        List<List<Schedule>>schedules = scheduleDao.findAllSchedulesByTimetableId(-1);
        assertTrue(schedules.isEmpty());
    }

}
