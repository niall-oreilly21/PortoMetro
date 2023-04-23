package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;

import java.util.List;

public interface InsertDaoInterface<T>
{
    void insert(T element) throws DaoException;
    void insert(T element, int timetableId) throws DaoException;
    List<List<T>> findAll(int timetableId) throws DaoException;
}
