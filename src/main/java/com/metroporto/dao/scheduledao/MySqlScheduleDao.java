package com.metroporto.dao.scheduledao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.InsertDaoInterface;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class MySqlScheduleDao extends MySqlDao<Schedule> implements ScheduleDaoInterface
{
    private StationDaoInterface stationDao;
    private Map<String, Station> stations;

    public MySqlScheduleDao()
    {
        stationDao = new MySqlStationDao();
        stations = new HashMap<>();
    }

    @Override
    public void insert(List<Schedule> schedules, int timetableId, int rowColumn) throws DaoException {
        try {
            // Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO schedules (timetable_id, station_id, row_number, departure_time) VALUES (?, ?, ?, ?)";

            // Disable auto-commit to start a transaction
            con.setAutoCommit(false);

            ps = con.prepareStatement(query);

            // Set the parameters for each schedule in the batch
            for (Schedule schedule : schedules) {
                ps.setInt(1, timetableId);
                ps.setString(2, schedule.getStation().getStationId());
                ps.setInt(3, rowColumn);
                ps.setTime(4, Time.valueOf(schedule.getDepartureTime()));
                ps.addBatch(); // Add the current set of parameter values to the batch
            }

            // Execute the batch and get the update counts
            int[] updateCounts = ps.executeBatch();

            // Validate the update counts
            for (int updateCount : updateCounts) {
                if (updateCount == PreparedStatement.EXECUTE_FAILED) {
                    throw new DaoException("Batch insert failed.");
                }
            }

            // Commit the transaction
            con.commit();

            // Enable auto-commit after the transaction is completed
            con.setAutoCommit(true);
        } catch (SQLException sqe) {
            // Rollback the transaction on error
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    // Log the rollback error, if any
                    e.printStackTrace();
                }
            }
            throw new DaoException("insert() " + sqe.getMessage());
        } finally {
            handleFinally("insert()");
        }
    }

    @Override
    public List<List<Schedule>> findAll(int timetableId) throws DaoException
    {
        long startTime = System.nanoTime();
        HashMap<Integer, List<Schedule>> scheduleMap = new HashMap<>();
        List<List<Schedule>> timetableSchedules = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT station_id, row_number, departure_time FROM schedules WHERE timetable_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, timetableId);

            rs = ps.executeQuery();

            while (rs.next())
            {
                int rowNumber = rs.getInt("row_number");
                Schedule schedule = createDto();

                // Add the schedule data to the map with row number as the key
                if (scheduleMap.containsKey(rowNumber))
                {
                    scheduleMap.get(rowNumber).add(schedule);
                }
                else
                {
                    List<Schedule> schedules = new ArrayList<>();
                    schedules.add(schedule);

                    scheduleMap.put(rowNumber, schedules);
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAll() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAll()");
        }

        timetableSchedules.addAll(scheduleMap.values());

        long endTime = System.nanoTime();

        // Calculate time taken in milliseconds
        long timeTakenInMillis = (endTime - startTime) / 1_000_000;

        System.out.println("Time taken: " + timeTakenInMillis + " ms");

        return timetableSchedules;
    }

    @Override
    protected Schedule createDto() throws SQLException
    {
        String stationId = rs.getString("station_id");
        LocalTime departureTime = rs.getTime("departure_time").toLocalTime();

        Station station;

        if (stations.containsKey(stationId))
        {
            station = stations.get(stationId);
        }
        else
        {
            // Fetch station data from database and add it to cache
            station = fetchStationDto(stationId);
            stations.put(stationId, station);
        }

        return new Schedule(station, departureTime);
    }

    private Station fetchStationDto(String stationId) throws SQLException
    {
        return stationDao.findStationByStationId(stationId);
    }

}
