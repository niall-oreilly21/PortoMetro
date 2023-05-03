package com.metroporto.dao;

import com.metroporto.dao.facilitydao.FacilityDaoInterface;
import com.metroporto.dao.facilitydao.MySqlFacilityDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MySqlFacilityDaoTest
{
    private FacilityDaoInterface facilityDao;

    @BeforeEach
    void setUp()
    {
        facilityDao = new MySqlFacilityDao();
    }

    @Test
    void findAllFacilitiesByStationNameAssertFalse() throws DaoException
    {
        System.out.println("\nfindAllFacilitiesByStationName() does not return a empty List for station name Senhor De Matosinhos.");
        List<Facility> facilities = facilityDao.findAllFacilitiesByStationName("Senhor De Matosinhos");
        assertFalse(facilities.isEmpty());
    }

    @Test
    void findAllFacilitiesByStationNameAssertTrue() throws DaoException
    {
        System.out.println("\nfindAllFacilitiesByStationName() returns a empty List for station name Porto.");
        List<Facility> facilities = facilityDao.findAllFacilitiesByStationName("Porto");
        assertTrue(facilities.isEmpty());
    }

}
