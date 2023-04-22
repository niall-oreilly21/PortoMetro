package com.metroporto.dao.stationdao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.facilitydao.FacilityDaoInterface;
import com.metroporto.dao.facilitydao.MySqlFacilityDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlStationDao extends MySqlDao<Station> implements StationDaoInterface
{
    private ZoneDaoInterface zoneDao;
    private FacilityDaoInterface facilityDao;

    public MySqlStationDao()
    {
        zoneDao = new MySqlZoneDao();
        facilityDao = new MySqlFacilityDao();
    }

    @Override
    public List<Station> findAllStations() throws DaoException
    {
        List<Station> stations = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM stations";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                stations.add(createDto());
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAllStations() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllStations()");
        }

        return stations;
    }

    @Override
    public Station findStationByStationId(String stationIdToBeFound) throws DaoException
    {
        Station station = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM stations WHERE station_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, stationIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                station = createDto();
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findStationByStationId() " + sqe.getMessage());
        } finally
        {
            handleFinally("findStationByStationId()");
        }

        return station;
    }

    @Override
    protected Station createDto() throws SQLException
    {
        String stationId = rs.getString("station_id");
        int zoneId = rs.getInt("zone_id");
        String stationName = rs.getString("station_name");

        Zone zone = zoneDao.findZoneByZoneId(zoneId);

        List<Facility>facilities = facilityDao.findAllFacilitiesByStationName(stationName);

        return new Station(stationId, zone, stationName, facilities);
    }

}
