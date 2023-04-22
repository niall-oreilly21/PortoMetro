package com.metroporto.dao.timetabledao;

import com.metroporto.dao.InsertDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Timetable;

import java.sql.SQLException;
import java.sql.Timestamp;

public class MySqlTimetableDao extends MySqlDao<Timetable> implements InsertDaoInterface<Timetable>
{
    @Override
    public void insert(Timetable timetable) throws DaoException
    {

    }

    @Override
    public void insert(Timetable timetable, int routeId) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();

            query = "INSERT INTO timetables (timetable_id, route_id, scheduled_day_type) VALUES\n" +
                    "(?, ?, ?)";

            ps = con.prepareStatement(query);

            ps.setInt(1,timetable.getTimetableId());
            ps.setInt(2, routeId);
            ps.setString(2, timetable.getTimeTableType().getLabel());

            //Use the prepared statement to execute the sql
            ps.executeUpdate();

        }
        catch (SQLException sqe)
        {
            throw new DaoException("insert() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insert()");
        }
    }

    @Override
    protected Timetable createDto() throws SQLException
    {
        return null;
    }
}
