package com.metroporto.dao.linedao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.routedao.RouteDaoInterface;
import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Route;
import com.metroporto.metro.Train;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlLineDao extends MySqlDao implements LineDaoInterface
{
    private RouteDaoInterface routeDao;
    private TrainDaoInterface trainDao;

    public MySqlLineDao()
    {
        routeDao = new MySqlRouteDao();
        trainDao = new MySqlTrainDao();
    }

    @Override
    public List<Line> findAllLines() throws DaoException
    {
        List<Line> lines = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM metro_lines";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                String lineId = rs.getString("line_id");
                String lineName = rs.getString("line_name");

                List<Route> routes = routeDao.findAllRoutesByLineId(lineId);
                List<Train>trains = trainDao.findAllTrainsByLineId(lineId);

                Line line = new Line(lineId, lineName, routes, trains);

                lines.add(line);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutes() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllRoutes()");
        }

        return lines;
    }
}
