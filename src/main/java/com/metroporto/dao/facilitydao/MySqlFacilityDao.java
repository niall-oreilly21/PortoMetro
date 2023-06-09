package com.metroporto.dao.facilitydao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySqlFacilityDao extends MySqlDao<Facility> implements FacilityDaoInterface
{
    private HashMap<Integer, Facility>facilities;

    public MySqlFacilityDao()
    {
        facilities = new HashMap<>();
    }

    @Override
    public List<Facility> findAll() throws DaoException
    {
        List<Facility> facilities = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query =
                    "SELECT * FROM facilities;";

            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                facilities.add(createDto());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAll() in MySqlFacilityDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAll() in MySqlFacilityDao");
        }

        return facilities;
    }

    @Override
    public List<Facility> findAllFacilitiesByStationName(String stationName) throws DaoException
    {
        List<Facility> facilities = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query =
                    "SELECT facilities.* FROM stations \n" +
                            "JOIN station_facilities ON stations.station_id = station_facilities.station_id\n" +
                            "JOIN facilities ON station_facilities.facility_id = facilities.facility_id \n" +
                            "WHERE stations.station_id IN(SELECT station_id \n" +
                            "FROM stations  WHERE station_name LIKE ?);";

            ps = con.prepareStatement(query);
            ps.setString(1, stationName);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                facilities.add(createDto());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllFacilitiesByStationName() in MySqlFacilityDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllFacilitiesByStationName() in MySqlFacilityDao");
        }

        return facilities;
    }

    @Override
    protected Facility createDto() throws SQLException
    {
        int facilityId = rs.getInt("facility_id");
        String facilityDescription = rs.getString("facility_description");

        Facility facility;

        if (facilities.containsKey(facilityId))
        {
            facility = facilities.get(facilityId);
        }
        else
        {
            facility = new Facility(facilityId, facilityDescription);
            facilities.put(facilityId, facility);
        }

        return facility;

    }
}
