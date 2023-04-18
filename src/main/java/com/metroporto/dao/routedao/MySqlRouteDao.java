package com.metroporto.dao.routedao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlRouteDao extends MySqlDao implements RouteDaoInterface
{
    private StationDaoInterface stationDao;

    public MySqlRouteDao()
    {
        stationDao = new MySqlStationDao();
    }

    @Override
    public List<Route> findAllRoutes() throws DaoException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Route> routes = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM routes";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                int routeId = rs.getInt("route_id");
                String stationId = rs.getString("end_station_id");

                Station station = stationDao.findStationByStationId(stationId);

                Route route = new Route(routeId, station);

                routes.add(route);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutes() " + sqe.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException sqe)
            {
                throw new DaoException("findAllRoutes() " + sqe.getMessage());
            }
        }

        return routes;
    }

    @Override
    public List<Route> findAllRoutesByLineId(String lineId) throws DaoException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Route> routes = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();

            String query =
                    "SELECT * FROM routes \n" +
                            "WHERE line_id = ?;";

            ps = con.prepareStatement(query);
            ps.setString(1, lineId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                int routeId = rs.getInt("route_id");
                String stationId = rs.getString("end_station_id");

                Station station = stationDao.findStationByStationId(stationId);

                Route route = new Route(routeId, station);

                routes.add(route);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutes() " + sqe.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException sqe)
            {
                throw new DaoException("findAllRoutes() " + sqe.getMessage());
            }
        }

        return routes;
    }
}
