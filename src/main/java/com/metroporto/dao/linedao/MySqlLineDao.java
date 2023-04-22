package com.metroporto.dao.linedao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Route;
import com.metroporto.metro.Train;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlLineDao extends MySqlDao<Line> implements LineDaoInterface
{
    private FindAllDaoInterface routeDao;
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
                lines.add(createDto());
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

    @Override
    protected Line createDto() throws SQLException
    {
        String lineId = rs.getString("line_id");
        String lineName = rs.getString("line_name");

        List<Route> routes = routeDao.findAllElementsById(lineId);
        List<Train>trains = trainDao.findAllTrainsByLineId(lineId);

        return new Line(lineId, lineName, routes, trains);
    }
}
