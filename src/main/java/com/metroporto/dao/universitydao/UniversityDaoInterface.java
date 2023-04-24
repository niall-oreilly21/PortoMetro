package com.metroporto.dao.universitydao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;

public interface UniversityDaoInterface extends FindAllDaoInterface<University>
{
    University findUniversityByUniversityId(String universityIdToBeFound) throws DaoException;
}
