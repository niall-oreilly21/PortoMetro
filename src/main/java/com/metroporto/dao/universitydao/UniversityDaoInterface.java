package com.metroporto.dao.universitydao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.users.User;

public interface UniversityDaoInterface extends FindAllDaoInterface<University>
{
    University findUniversityByUniversityId(String universityId) throws DaoException;
    void insertUniversityForStudent(User user) throws DaoException;
}
