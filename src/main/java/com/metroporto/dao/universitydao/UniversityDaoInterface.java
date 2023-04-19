package com.metroporto.dao.universitydao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;

public interface UniversityDaoInterface
{
    University findUniversityByUniversityId(String universityId) throws DaoException;
}
