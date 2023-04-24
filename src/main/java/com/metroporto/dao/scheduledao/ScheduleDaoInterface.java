package com.metroporto.dao.scheduledao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;

import java.util.List;

public interface ScheduleDaoInterface
{
    void insertSchedulesByRow(List<Schedule> element, int timetableId, int rowNumber) throws DaoException;
    List<List<Schedule>> findAllSchedulesByTimetableId(int timetableId) throws DaoException;
}
