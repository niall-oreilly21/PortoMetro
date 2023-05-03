package com.metroporto.dao.timetabledao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.scheduledao.MySqlScheduleDao;
import com.metroporto.enums.TimetableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlTimetableDao extends MySqlDao<Timetable> implements TimetableDaoInterface
{
    private MySqlScheduleDao scheduleDao;

    public MySqlTimetableDao()
    {
        scheduleDao = new MySqlScheduleDao();
    }

    @Override
    public List<Timetable> findAllTimetablesByRouteId(int routeId) throws DaoException
    {
        List<Timetable> timetables = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM timetables WHERE route_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, routeId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                timetables.add(createDto());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllTimetablesByRouteId() in MySqlTimetableDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllTimetablesByRouteId() in MySqlTimetableDao");
        }

        return timetables;
    }

    @Override
    public void insertTimetableByRouteId(Timetable timetable, int routeId) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO timetables (route_id, timetable_day_type) VALUES\n" +
                    "(?, ?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            //ps.setInt(1,timetable.getTimetableId());
            ps.setInt(1, routeId);
            ps.setString(2, timetable.getTimeTableType().getLabel());

            //Use the prepared statement to execute the sql
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                // Retrieve the generated keys
                rs = ps.getGeneratedKeys();

                if (rs.next())
                {
                    int timetableId = rs.getInt(1); // Retrieve the generated timetableid
                    timetable.setTimetableId(timetableId); // Set the timetableid in the Timetable object
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertTimetableByRouteId() in MySqlTimetableDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertTimetableByRouteId() in MySqlTimetableDao");
        }
    }

    @Override
    protected Timetable createDto() throws SQLException
    {
        int timetableId = rs.getInt("timetable_id");
        TimetableType TimetableType = enumLabelConverter.fromLabel(rs.getString("timetable_day_type"), com.metroporto.enums.TimetableType.class);
        List<List<Schedule>> timetableSchedules = scheduleDao.findAllSchedulesByTimetableId(timetableId);
        return new Timetable(timetableId, TimetableType, timetableSchedules);
    }
}
