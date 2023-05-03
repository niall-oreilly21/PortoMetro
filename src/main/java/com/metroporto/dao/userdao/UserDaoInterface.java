package com.metroporto.dao.userdao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.RemoveDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

public interface UserDaoInterface extends FindAllDaoInterface<User>, RemoveDaoInterface<User>
{
    User findUserByEmail(String email) throws DaoException;
    boolean insertUser(User user) throws DaoException;
    boolean updateEmail(User user) throws DaoException;
    boolean updatePassword(User user) throws DaoException;
    boolean updateFirstName(User user) throws DaoException;
    boolean updateLastName(User user) throws DaoException;
}
