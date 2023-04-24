package com.metroporto.dao.timetabledao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Timetable;

import java.util.List;

public interface TimetableDaoInterface
{
    List<Timetable> findAllTimetablesByRouteId(int routeId) throws DaoException;
    void insertTimetableByRouteId(Timetable timetable, int routeId) throws DaoException;
}
