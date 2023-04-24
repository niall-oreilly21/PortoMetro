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

public class MySqlLineDao extends MySqlDao<Line> implements LineDaoInterface
{
    private RouteDaoInterface routeDao;
    private TrainDaoInterface trainDao;

    public MySqlLineDao()
    {
        routeDao = new MySqlRouteDao();
        trainDao = new MySqlTrainDao();
    }

    @Override
    public List<Line> findAll() throws DaoException
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
            throw new DaoException("findAll() in MySqlLineDao " + sqe.getMessage());
        } finally
        {
            handleFinally("findAll() in MySqlLineDao");
        }

        return lines;
    }

    @Override
    public Line findLineByLineId(String lineId) throws DaoException
    {
        Line line = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM metro_lines WHERE line_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, lineId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                line = createDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findLineByLineId() in MySqlLineDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findLineByLineId() in MySqlLineDao");
        }

        return line;
    }

    @Override
    protected Line createDto() throws SQLException
    {
        String lineId = rs.getString("line_id");
        String lineName = rs.getString("line_name");

        List<Route> routes = routeDao.findAllRoutesByLineId(lineId);
        List<Train>trains = trainDao.findAllTrainsByLineId(lineId);

        return new Line(lineId, lineName, routes, trains);
    }
}
