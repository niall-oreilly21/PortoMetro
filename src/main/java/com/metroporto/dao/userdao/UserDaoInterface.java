package com.metroporto.dao.userdao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

import java.util.List;

public interface UserDaoInterface
{
    List<User> findAllUsers() throws DaoException;
    //User findPassenger(int userId) throws DaoException;
}
