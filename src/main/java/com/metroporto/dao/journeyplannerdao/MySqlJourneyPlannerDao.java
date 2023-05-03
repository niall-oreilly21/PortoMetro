package com.metroporto.dao.journeyplannerdao;

import com.metroporto.metro.JourneyPlanner;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.TimetableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import com.metroporto.users.Passenger;
import com.metroporto.users.User;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlJourneyPlannerDao extends MySqlDao<JourneyPlanner> implements JourneyPlannerDaoInterface
{
    StationDaoInterface stationDao;

    public MySqlJourneyPlannerDao()
    {
        stationDao = new MySqlStationDao();
    }
    @Override
    public List<JourneyPlanner> findAllJourneyPlannersByUserId(int userId) throws DaoException
    {
        List<JourneyPlanner> journeyPlanners = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM journey_planners WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                journeyPlanners.add(createDto());
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAllJourneyPlannersByUserId() in MySqlJourneyPlannerDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllJourneyPlannersByUserId() in MySqlJourneyPlannerDao");
        }

        return journeyPlanners;
    }

    @Override
    public boolean insertJourneyPlannerForPassenger(User user, JourneyPlanner journeyPlanner) throws DaoException
    {
        if(user instanceof Passenger)
        {
            if(((Passenger) user).addJourneyPlanner(journeyPlanner))
            {
                return insertJourneyPlannerForPassenger(user.getUserId(), journeyPlanner);
            }
        }

        return false;
    }

    public boolean insertJourneyPlannerForPassenger(int userId, JourneyPlanner journeyPlanner) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO journey_planners (user_id, start_station_id, end_station_id, start_time, timetable_day_type) VALUES\n" +
                    "(?, ?, ?, ?, ?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prepareSqlStatement(journeyPlanner, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                // Retrieve the generated keys
                rs = ps.getGeneratedKeys();

                if (rs.next())
                {
                    int journeyPlannerId = rs.getInt(1);
                    journeyPlanner.setJourneyPlannerId(journeyPlannerId);
                }
                return true;
            }
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao");
        }

    return false;
    }


    private void prepareSqlStatement(JourneyPlanner journeyPlanner, int userId) throws SQLException
    {
        ps.setInt(1, userId);
        ps.setString(2, journeyPlanner.getStartStation().getStationId());
        ps.setString(3, journeyPlanner.getEndStation().getStationId());
        ps.setTime(4, (Time.valueOf(journeyPlanner.getStartTime())));
        ps.setString(5, journeyPlanner.getTimetableDayType().getLabel());
    }

    @Override
    protected JourneyPlanner createDto() throws SQLException
    {
        int journeyPlannerId = rs.getInt("journey_planner_id");
        Station startStation  = getCachedStation(rs.getString("start_station_id"), stationDao);
        Station endStation = getCachedStation(rs.getString("end_station_id"),stationDao);
        LocalTime startTime = rs.getTime("start_time").toLocalTime();
        TimetableType TimetableDayType = enumLabelConverter.fromLabel(rs.getString("timetable_day_type"), TimetableType.class);
        return new JourneyPlanner(journeyPlannerId, startStation, endStation, startTime, TimetableDayType);
    }

    @Override
    public boolean remove(JourneyPlanner journeyPlanner) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "DELETE FROM journey_planners WHERE journey_planner_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, journeyPlanner.getJourneyPlannerId());

            //Use the prepared statement to execute the sql
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                query = "SELECT * FROM journey_planners WHERE journey_planner_id = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, journeyPlanner.getJourneyPlannerId());
                rs = ps.executeQuery();

                if (!rs.next())
                {
                    return true;
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("remove() in MySqlJourneyPlannerDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao");
        }

        return false;
    }
}
