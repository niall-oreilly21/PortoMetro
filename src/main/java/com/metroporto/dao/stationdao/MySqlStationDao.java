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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlStationDao extends MySqlDao<Station> implements StationDaoInterface
{
    private ZoneDaoInterface zoneDao;
    private FacilityDaoInterface facilityDao;
    private Map<Integer, Zone> zones;
    private Map<String, Facility> facilities;

    public MySqlStationDao()
    {
        zoneDao = new MySqlZoneDao();
        facilityDao = new MySqlFacilityDao();
        zones = new HashMap<>();
        facilities = new HashMap<>();
    }

    @Override
    public List<Station> findAll() throws DaoException
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
            throw new DaoException("findAll() in MySqlStationDao " + sqe.getMessage());
        } finally
        {
            handleFinally("findAll() in MySqlStationDao");
        }

        return stations;
    }

    @Override
    public Station findStationByStationId(String stationId) throws DaoException
    {
        Station station = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM stations WHERE station_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, stationId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                station = createDto();
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findStationByStationId() in MySqlStationDao " + sqe.getMessage());
        } finally
        {
            handleFinally("findStationByStationId() in MySqlStationDao");
        }

        return station;
    }

    @Override
    protected Station createDto() throws SQLException
    {
        String stationId = rs.getString("station_id");
        int zoneId = rs.getInt("zone_id");
        String stationName = rs.getString("station_name");

        Zone zone;

        if (zones.containsKey(zoneId))
        {
            zone = zones.get(zoneId);
        }
        else
        {
            zone = fetchZoneDto(zoneId);
            zones.put(zoneId, zone);
        }

        List<Facility>facilities = facilityDao.findAllFacilitiesByStationName(stationName);

        return new Station(stationId, zone, stationName, facilities);
    }

    private Zone fetchZoneDto(int zoneId) throws SQLException
    {
        return zoneDao.findZoneByZoneId(zoneId);
    }
}
