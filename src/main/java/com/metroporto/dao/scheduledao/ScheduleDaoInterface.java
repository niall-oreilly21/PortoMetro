package com.metroporto.dao.scheduledao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;

import java.util.List;

public interface ScheduleDaoInterface
{
    void insert(List<Schedule> element, int timetableId, int rowColumn) throws DaoException;
    List<List<Schedule>> findAll(int timetableId) throws DaoException;
}
