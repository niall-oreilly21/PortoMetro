package com.metroporto.dao;

import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.routedao.RouteDaoInterface;
import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import com.metroporto.metro.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MySqlRouteDaoTest
{
    private RouteDaoInterface routeDao;

    @BeforeEach
    void setUp()
    {
        routeDao = new MySqlRouteDao();
    }

    @Test
    void findAllRoutesByLineIdAssertFalse() throws DaoException
    {
        System.out.println("\nfindAllTrainsByLine() does not return a empty List for line A.");
        List<Route> routes = routeDao.findAllRoutesByLineId("A");
        assertFalse(routes.isEmpty());
    }

    @Test
    void findAllRoutesByLineIdAssertTrue() throws DaoException
    {
        System.out.println("\nfindAllTrainsByLine() returns a empty List for line M.");
        List<Route> routes = routeDao.findAllRoutesByLineId("M");
        assertTrue(routes.isEmpty());
    }

    @Test
    void findRouteByEndStationIdNotNull() throws DaoException
    {
        System.out.println("\nfindRouteByEndStationId() returns not null when the endStationId = SEM (is in database).");
        String stationId = "SEM";
        Route route = routeDao.findRouteByEndStationId(stationId);
        assertNotNull(route);
    }

    @Test
    void findRouteByEndStationIdNull() throws DaoException
    {
        System.out.println("\nfindRouteByEndStationId() returns null when the TrainId = ERT (is not in database).");
        String stationId = "ERT";
        Route route = routeDao.findRouteByEndStationId(stationId);
        assertNull(route);
    }

}
