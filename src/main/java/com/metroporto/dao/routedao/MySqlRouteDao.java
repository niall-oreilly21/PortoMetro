package com.metroporto.dao.routedao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.dao.timetabledao.MySqlTimetableDao;
import com.metroporto.dao.timetabledao.TimetableDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import com.metroporto.metro.Station;
import com.metroporto.metro.Timetable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlRouteDao extends MySqlDao<Route> implements FindAllDaoInterface<Route, String>
{
    private StationDaoInterface stationDao;
    private TimetableDaoInterface timetableDao;

    public MySqlRouteDao()
    {
        stationDao = new MySqlStationDao();
        timetableDao = new MySqlTimetableDao();
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
                routes.add(createDto());
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
                routes.add(createDto());
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
    protected Route createDto() throws SQLException
    {
        int routeId = rs.getInt("route_id");
        String stationId = rs.getString("end_station_id");

        Station station = stationDao.findStationByStationId(stationId);
        List<Timetable> timetables = timetableDao.findAllTimetablesByRouteId(routeId);

        return new Route(routeId, station, timetables);
    }

}
