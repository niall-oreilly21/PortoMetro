package com.metroporto.dao.zonedao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlZoneDao extends MySqlDao implements ZoneDaoInterface
{
    @Override
    public Zone findZoneByZoneId(int zoneIdToBeFound) throws DaoException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Zone zone = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM zones WHERE zone_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, zoneIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                int zoneId = rs.getInt("zone_id");
                String zoneName = rs.getString("zone_name");

                zone = new Zone(zoneId, zoneName);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findZoneByZoneId() " + sqe.getMessage());
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
                throw new DaoException("findZoneByZoneId() " + sqe.getMessage());
            }
        }

        return zone;
    }
}
