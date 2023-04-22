package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;

import java.util.List;

public interface FindAllDaoInterface<T, E>
{
    List<T> findAll() throws DaoException;
    List<T> findAllElementsById(E id) throws DaoException;
}
