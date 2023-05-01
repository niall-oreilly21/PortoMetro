package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;

public interface RemoveDaoInterface<T>
{
     boolean remove(T element) throws DaoException;
}
