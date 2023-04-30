package com.metroporto.dao.journeyplannerdao;

import com.metroporto.JourneyPlanner;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;
import org.apache.poi.ss.formula.functions.T;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlJourneyPlannerDao extends MySqlDao<JourneyPlanner> implements JourneyPlannerDaoInterface
{

    @Override
    public List<JourneyPlanner> findAllJourneyPlannersByUserId(int userId) throws DaoException
    {
        List<JourneyPlanner> journeyPlanners = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT journey_planners.* FROM journey_planners\n" +
                    "JOIN passengers_journey_planners ON journey_planners.journey_planner_id = passengers_journey_planners.journey_planner_id\n" + // fixed the typo here
                    "WHERE passengers_journey_planners.user_id = ?";

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
        boolean journeyPlannerInserted = false;

        if(user instanceof Passenger)
        {
            if(((Passenger) user).addJourneyPlanner(journeyPlanner))
            {
                if(insertJourneyPlanner(journeyPlanner))
                {
                    if(insertJourneyPlannerForPassenger(user.getUserId(), journeyPlanner.getJourneyPlannerId()))
                    {
                        journeyPlannerInserted = true;
                    }
                }
            }
        }
        return journeyPlannerInserted;
    }

    public boolean insertJourneyPlanner(JourneyPlanner journeyPlanner) throws DaoException
    {
        boolean isInserted = false;

        if(!checkJourneyPlannerExists(journeyPlanner))
        {
            try
            {
                //Get a connection to the database
                con = this.getConnection();
                query = "INSERT INTO journey_planners (start_station_id, end_station_id, start_time, timetable_day_type) VALUES\n" +
                        "(?, ?, ?, ?)";

                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                prepareSqlStatement(journeyPlanner);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    isInserted = true;

                    // Retrieve the generated keys
                    rs = ps.getGeneratedKeys();

                    if (rs.next())
                    {
                        int journeyPlannerId = rs.getInt(1);
                        journeyPlanner.setJourneyPlannerId(journeyPlannerId);
                    }
                }
            }
            catch (SQLIntegrityConstraintViolationException e)
            {
                // Handle duplicate entry error
                System.out.println("Duplicate entry found in the database");
            }
            catch (SQLException sqe)
            {
                throw new DaoException("insertJourneyPlanner() in MySqlJourneyPlannerDao " + sqe.getMessage());
            }
            finally
            {
                handleFinally("insertJourneyPlanner() in MySqlJourneyPlannerDao");
            }
        }

        return isInserted;
    }

    private boolean insertJourneyPlannerForPassenger(int userId, int journeyPlannerId) throws DaoException
    {
        boolean isInserted = false;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO passengers_journey_planners (user_id, journey_planner_id) VALUES\n" +
                    "(?, ?)";

            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, journeyPlannerId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                isInserted = true;
            }
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao");
        }

        return isInserted;
    }

    private boolean checkJourneyPlannerExists(JourneyPlanner journeyPlanner) throws DaoException
    {
        boolean journeyPlannerExists = true;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM journey_planners WHERE start_station_id = ? AND end_station_id = ? AND start_time = ? AND timetable_day_type = ?";

            ps = con.prepareStatement(query);
            prepareSqlStatement(journeyPlanner);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            if (!rs.next())
            {
                journeyPlannerExists = false;
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("checkJourneyPlannerExists() in MySqlJourneyPlannerDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("checkJourneyPlannerExists() in MySqlJourneyPlannerDao");
        }

        return journeyPlannerExists;
    }

    private void prepareSqlStatement(JourneyPlanner journeyPlanner) throws SQLException
    {
        ps.setString(1, journeyPlanner.getStartStation().getStationId());
        ps.setString(2, journeyPlanner.getEndStation().getStationId());
        ps.setTime(3, (Time.valueOf(journeyPlanner.getStartTime())));
        ps.setString(4, journeyPlanner.getTimetableDayType().getLabel());
    }

    @Override
    protected JourneyPlanner createDto() throws SQLException
    {
        int journeyPlannerId = rs.getInt("journey_planner_id");
        Station startStation  = getStation(rs.getString("start_station_id"));
        Station endStation = getStation(rs.getString("end_station_id"));
        LocalTime startTime = rs.getTime("start_time").toLocalTime();
        TimeTableType TimetableDayType = enumLabelConverter.fromLabel(rs.getString("timetable_day_type"), TimeTableType.class);
        return new JourneyPlanner(journeyPlannerId, startStation, endStation, startTime, TimetableDayType);
    }
}
