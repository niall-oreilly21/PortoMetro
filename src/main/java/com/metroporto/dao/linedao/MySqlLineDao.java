package com.metroporto.dao.linedao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.routedao.RouteDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Route;
import com.metroporto.metro.Station;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlLineDao extends MySqlDao implements LineDaoInterface
{
    private RouteDaoInterface routeDao;

    public MySqlLineDao()
    {
         routeDao = new MySqlRouteDao();
    }

    @Override
    public List<Line> findAllLines() throws DaoException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Line> lines = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM metro_lines";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                String lineId = rs.getString("line_id");
                String lineName = rs.getString("line_name");

                List<Route> routes = routeDao.findAllRoutesByLineId(lineId);

                Line line = new Line(lineId, lineName, routes);

                lines.add(line);
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

        return lines;
    }
}
