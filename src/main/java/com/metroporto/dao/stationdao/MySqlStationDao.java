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

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlStationDao extends MySqlDao implements StationDaoInterface
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Station> stations = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM stations";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                stations.add(createStation(rs));
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllStations() " + sqe.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException sqe)
            {
                throw new DaoException("findAllStations() " + sqe.getMessage());
            }
        }

        return stations;
    }

    @Override
    public Station findStationByStationId(String stationIdToBeFound) throws DaoException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Station station = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM stations WHERE station_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, stationIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                station = createStation(rs);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findStationByStationId() " + sqe.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException sqe)
            {
                throw new DaoException("findStationByStationId() " + sqe.getMessage());
            }
        }

        return station;
    }

    private Station createStation(ResultSet rs) throws SQLException
    {
        Station station = null;

        try
        {
            String stationId = rs.getString("station_id");
            int zoneId = rs.getInt("zone_id");
            String stationName = rs.getString("station_name");

            Zone zone = zoneDao.findZoneByZoneId(zoneId);

            List<Facility>facilities = facilityDao.findAllFacilitiesByStationName(stationName);

            station = new Station(stationId, zone, stationName, facilities);
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findStationByStationId() " + sqe.getMessage());
        }
        return station;
    }
}
