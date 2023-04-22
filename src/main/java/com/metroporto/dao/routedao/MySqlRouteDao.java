package com.metroporto.dao.routedao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import com.metroporto.metro.Station;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlRouteDao extends MySqlDao<Route> implements FindAllDaoInterface<Route, String>
{
    private StationDaoInterface stationDao;

    public MySqlRouteDao()
    {
        stationDao = new MySqlStationDao();
    }

    @Override
    public List<Route> findAll() throws DaoException
    {
        List<Route> routes = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM routes";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                routes.add(createElement());
            }

        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutes() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllRoutes()");
        }

        return routes;
    }

    @Override
    public List<Route> findAllElementsById(String id) throws DaoException
    {
        List<Route> routes = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();

            query =
                    "SELECT * FROM routes \n" +
                            "WHERE line_id = ?;";

            ps = con.prepareStatement(query);
            ps.setString(1, id);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                routes.add(createElement());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutesByLineId() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllRoutesByLineId()");
        }

        return routes;
    }

    @Override
    protected Route createElement() throws SQLException
    {
        int routeId = rs.getInt("route_id");
        String stationId = rs.getString("end_station_id");

        Station station = stationDao.findStationByStationId(stationId);

        return new Route(routeId, station);
    }
}
