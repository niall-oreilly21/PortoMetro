package com.metroporto.dao.scheduledao;

import com.metroporto.dao.InsertDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;

import java.sql.SQLException;
import java.sql.Timestamp;

public class MySqlScheduleDao extends MySqlDao<Schedule> implements InsertDaoInterface<Schedule>
{
    @Override
    public void insert(Schedule schedule) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();

             query = "INSERT INTO schedules (schedule_id, station_id, departure_time) VALUES\n" +
                    "(?, ?, ?)";

            ps = con.prepareStatement(query);

            ps.setInt(1,1);
            ps.setString(2, schedule.getStation().getStationId());
            ps.setTimestamp(3, Timestamp.valueOf(schedule.getDepartureTime().toString()));

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
    protected Schedule createElement() throws SQLException
    {
        return null;
    }
}
