package com.metroporto.dao.timetabledao;

import com.metroporto.dao.InsertDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.routedao.MySqlRouteDao;
import com.metroporto.dao.scheduledao.MySqlScheduleDao;
import com.metroporto.enums.CardAccessType;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
            throw new DaoException("findAllTimetablesByRouteId() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllTimetablesByRouteId()");
        }

        return timetables;
    }

    @Override
    public void insert(Timetable timetable, int routeId) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO timetables (route_id, scheduled_day_type) VALUES\n" +
                    "(?, ?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            //ps.setInt(1,timetable.getTimetableId());
            ps.setInt(1, routeId);
            ps.setString(2, timetable.getTimeTableType().getLabel());

            //Use the prepared statement to execute the sql
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                rs = ps.getGeneratedKeys();

                if (rs.next())
                {
                    int timetableId = rs.getInt(1); // Retrieve the generated timetableid
                    timetable.setTimetableId(timetableId); // Set the timetableid in the Timetable object
                }
            }

            if (rs.next())
            {
                int timetableId = rs.getInt(1); // assuming the timetableId column is the first column in the generated keys result set
                timetable.setTimetableId(timetableId); // set the retrieved timetableId to the timetable object
            }

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
        int timetableId = rs.getInt("timetable_id");
        TimeTableType TimetableType = enumLabelConverter.fromLabel(rs.getString("scheduled_day_type"), TimeTableType.class);
        List<List<Schedule>> timetableSchedules = scheduleDao.findAll(timetableId);
        return new Timetable(timetableId, TimetableType, timetableSchedules);
    }
}
