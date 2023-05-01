package com.metroporto.dao.zonedao;

import com.metroporto.cards.Card;
import com.metroporto.cards.GreyCard;
import com.metroporto.cards.StudentCard;
import com.metroporto.cards.TourCard;
import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;
import com.metroporto.users.Passenger;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class MySqlZoneDao extends MySqlDao<Zone> implements ZoneDaoInterface
{
    @Override
    public List<Zone> findAll() throws DaoException
    {
        List<Zone> zones = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM zones";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                zones.add(createDto());
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAll() in MySqlZoneDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAll() in MySqlZoneDao");
        }

        return zones;
    }

    @Override
    public Zone findZoneByZoneId(int zoneId) throws DaoException
    {
        Zone zone = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM zones WHERE zone_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, zoneId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                zone = createDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findZoneByZoneId() in MySqlZoneDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findZoneByZoneId() in MySqlZoneDao");
        }

        return zone;
    }

    @Override
    public List<Zone> findAllZonesByZoneId(String cardId) throws DaoException
    {
        List<Zone> zones = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM zones JOIN cards_zones ON zones.zone_id = cards_zones.zone_id WHERE cards_zones.card_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, cardId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                zones.add(createDto());
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAllZonesByZoneId() in ZonesDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAllZonesByZoneId() in ZonesDao()");
        }

        return zones;
    }

    @Override
    public void insertZonesForCard(String cardId, int zoneId) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "INSERT INTO cards_zones (card_id, zone_id) VALUES\n" +
                    "(?, ?)";

            ps = con.prepareStatement(query);
            ps.setString(1, cardId);
            ps.setInt(2, zoneId);
            ps.executeUpdate();

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertZonesForCard() in ZonesDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertZonesForCard() in ZonesDao()");
        }
    }

    @Override
    protected Zone createDto() throws SQLException
    {
        int zoneId = rs.getInt("zone_id");
        String zoneName = rs.getString("zone_name");

        return new Zone(zoneId, zoneName);
    }
}
