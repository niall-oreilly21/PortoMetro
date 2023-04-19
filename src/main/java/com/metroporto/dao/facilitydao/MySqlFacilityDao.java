package com.metroporto.dao.facilitydao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlFacilityDao extends MySqlDao implements FacilityDaoInterface
{
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
                int facilityId = rs.getInt("facility_id");
                String facilityDescription = rs.getString("facility_description");

                Facility facility = new Facility(facilityId, facilityDescription);

                facilities.add(facility);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllFacilitiesByStationName() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllFacilitiesByStationName()");
        }

        return facilities;
    }
}
