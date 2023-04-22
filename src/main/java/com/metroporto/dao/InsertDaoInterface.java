package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;

import java.util.List;

public interface InsertDaoInterface<T>
{
    void insert(T element) throws DaoException;
}
