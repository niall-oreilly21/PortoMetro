package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;

import java.util.List;

public interface FindAllDaoInterface<T>
{
    List<T> findAll() throws DaoException;
}
