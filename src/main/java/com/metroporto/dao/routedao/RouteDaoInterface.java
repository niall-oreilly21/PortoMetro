package com.metroporto.dao.routedao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;

import java.util.List;

public interface RouteDaoInterface
{
    List<Route> findAllRoutes() throws DaoException;
    List<Route> findAllRoutesByLineId(String lineId) throws DaoException;
}
