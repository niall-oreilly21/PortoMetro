package com.metroporto.dao.zonedao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlZoneDao extends MySqlDao implements ZoneDaoInterface
{
    @Override
    public Zone findZoneByZoneId(int zoneIdToBeFound) throws DaoException
    {
        Zone zone = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM zones WHERE zone_id = ?";
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
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findZoneByZoneId() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findZoneByZoneId()");
        }

        return zone;
    }

    @Override
    public List<Zone> findAllZonesByZoneId(int zoneIdToBeFound) throws DaoException
    {
        List<Zone> zones = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM zones JOIN cards_zones ON zones.zone_id = cards_zones.zone_id WHERE cards_zones.card_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, zoneIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                int zoneId = rs.getInt("zone_id");
                String zoneName = rs.getString("zone_name");

                zones.add(new Zone(zoneId, zoneName));
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAllZonesByZoneId() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllZonesByZoneId()");
        }

        return zones;
    }
}
