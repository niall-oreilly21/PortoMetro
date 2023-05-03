package com.metroporto.dao;

import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;
import com.metroporto.metro.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MySqlZoneDaoTest
{
    private ZoneDaoInterface zoneDao;

    @BeforeEach
    void setUp()
    {
        zoneDao = new MySqlZoneDao();
    }

    @Test
    void findZoneByZoneIdNotNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns not null for zone id 1.");
        Zone zone = zoneDao.findZoneByZoneId(1);
        assertNotNull(zone);
    }

    @Test
    void findZoneByZoneIdNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns null for zone id 20.");
        Zone zone = zoneDao.findZoneByZoneId(20);
        assertNull(zone);
    }

    @Test
    void findZoneByZoneNameNotNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns not null for zone name VCD3.");
        Zone zone = zoneDao.findZoneByZoneName("VCD3");
        assertNotNull(zone);
    }

    @Test
    void findZoneByZoneNameNull() throws DaoException
    {
        System.out.println("\nfindZoneByZoneId() returns not null for zone name VCD90.");
        Zone zone = zoneDao.findZoneByZoneName("VCD90");
        assertNull(zone);
    }

}
