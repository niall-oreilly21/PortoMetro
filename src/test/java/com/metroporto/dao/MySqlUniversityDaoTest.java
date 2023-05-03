package com.metroporto.dao;

import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.metro.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MySqlUniversityDaoTest
{
    private UniversityDaoInterface universityDao;

    @BeforeEach
    void setUp()
    {
        universityDao = new MySqlUniversityDao();
    }

    @Test
    void findUniversityByUniverisityIdNotNull() throws DaoException
    {
        System.out.println("\nfindUniversityByUniverisityId() returns not null for university id UOP.");
        University university = universityDao.findUniversityByUniversityId("UOP");
        assertNotNull(university);
    }

    @Test
    void findUniversityByUniverisityIdNull() throws DaoException
    {
        System.out.println("\nfindUniversityByUniverisityId() returns not null for university id UPP.");
        University university = universityDao.findUniversityByUniversityId("UOP");
        assertNotNull(university);
    }
}
