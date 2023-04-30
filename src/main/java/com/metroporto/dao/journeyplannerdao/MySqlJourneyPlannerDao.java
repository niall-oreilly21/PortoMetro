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
        boolean journeyPlannerInserted = false;

        if(user instanceof Passenger)
        {
            if(((Passenger) user).addJourneyPlanner(journeyPlanner))
            {
                    if(insertJourneyPlannerForPassenger(user.getUserId(), journeyPlanner))
                    {
                        journeyPlannerInserted = true;
                    }
                }
            }

        return journeyPlannerInserted;
    }

    public boolean insertJourneyPlannerForPassenger(int userId, JourneyPlanner journeyPlanner) throws DaoException
    {
        boolean isInserted = false;

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
                throw new DaoException("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao " + sqe.getMessage());
            }
            finally
            {
                handleFinally("insertJourneyPlannerForPassenger() in MySqlJourneyPlannerDao");
            }


        return isInserted;
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
        TimeTableType TimetableDayType = enumLabelConverter.fromLabel(rs.getString("timetable_day_type"), TimeTableType.class);
        return new JourneyPlanner(journeyPlannerId, startStation, endStation, startTime, TimetableDayType);
    }
}
