package com.metroporto.dao.createdatabase;

import com.metroporto.exceptions.DaoException;

public interface CreatePortoMetroDataBaseInterface
{
     void runSqlFile(String filePath) throws DaoException;
     void createDatabase() throws DaoException;
     boolean checkDatabaseExists() throws DaoException;

}
