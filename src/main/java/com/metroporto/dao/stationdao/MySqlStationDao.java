package com.metroporto.dao.stationdao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlStationDao extends MySqlDao implements StationDaoInterface
{
    private ZoneDaoInterface zoneDao = new MySqlZoneDao();

    @Override
    public List<Station> findAllStations() throws DaoException
    {
        Connection con = null;
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
                String stationID = rs.getString("station_id");
                int zoneId = rs.getInt("zone_id");
                String stationName = rs.getString("station_name");

                Zone zone = zoneDao.findZoneByZoneId(zoneId);

                Station station = new Station(stationID, zone, stationName);

                stations.add(station);
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
}
