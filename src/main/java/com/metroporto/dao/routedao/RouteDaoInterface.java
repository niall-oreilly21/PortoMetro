package com.metroporto.dao.routedao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;

import java.util.List;

public interface RouteDaoInterface extends FindAllDaoInterface<Route>
{
    Route findRouteByEndStationId(String endStationId) throws DaoException;
    List<Route> findAllRoutesByLineId(String lineId) throws DaoException;
}
