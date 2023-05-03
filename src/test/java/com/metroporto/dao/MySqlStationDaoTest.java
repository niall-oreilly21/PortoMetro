package com.metroporto.dao;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MySqlStationDaoTest
{
    private StationDaoInterface stationDao;

    @BeforeEach
    void setUp()
    {
        stationDao = new MySqlStationDao();
    }

    @Test
    void findStationByStationIdNotNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns not null for station id SEM.");
        Station station = stationDao.findStationByStationId("SEM");
        assertNotNull(station);
    }

    @Test
    void findStationByStationIdNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns null for station id ERT.");
        Station station = stationDao.findStationByStationId("ERT");
        assertNull(station);
    }
}
