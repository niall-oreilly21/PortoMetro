package com.metroporto.dao.userdao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

public interface UserDaoInterface extends FindAllDaoInterface<User>
{
    User findUserByEmail(String email) throws DaoException;
    void insertUser(User user) throws DaoException;

}
