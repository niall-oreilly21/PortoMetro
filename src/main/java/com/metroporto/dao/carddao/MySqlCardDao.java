package com.metroporto.dao.carddao;

import com.metroporto.cards.*;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Zone;
import com.metroporto.users.Passenger;
import com.metroporto.users.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlCardDao extends MySqlDao<Card> implements CardDaoInterface
{
    private ZoneDaoInterface zoneDao;

    public MySqlCardDao()
    {
        zoneDao = new MySqlZoneDao();
    }

    @Override
    public List<Card> findAll() throws DaoException
    {
        List<Card> cards = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query =
                    "SELECT * FROM all_cards;";

            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                cards.add(createDto());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAll() in MySqlCardDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAll() in MySqlCardDao");
        }

        return cards;
    }

    @Override
    public Card findCardByCardId(int userId) throws DaoException
    {
        Card card = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM all_cards WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                card = createDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findCardByCardId() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findCardByCardId() in MySqlCardDao");
        }

        return card;
    }

    @Override
    public boolean insertCardForPassenger(User user) throws DaoException
    {
        Card card = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "INSERT INTO cards (user_id, card_type, access_type, card_price) VALUES\n" +
                    "(?, ?, ?, ?)";

            ps = con.prepareStatement(query);
            ps.setInt(1, user.getUserId());

            String cardType;

            if(user instanceof Passenger)
            {
                card = ((Passenger) user).getMetroCard();

                if(card != null)
                {

                    if(card instanceof GreyCard)
                    {
                        if(card instanceof StudentCard)
                        {
                            cardType = "student card";
                        }
                        else
                        {
                            cardType = "grey card";
                        }
                    }
                    else
                    {
                        if(card instanceof TourCard)
                        {
                            cardType = "tour card";
                        }
                        else
                        {
                            cardType = "blue card";
                        }
                    }

                    ps.setString(2, cardType);
                    ps.setString(3, card.getCardAccessType().getLabel());
                    //ps.setDouble(4, card.getCardPrice());
                }

            }

            //Use the prepared statement to execute the sql
            int rowsAffected = 0;

            if(card != null)
            {
                rowsAffected = ps.executeUpdate();
            }


            if (rowsAffected > 0)
            {
                int cardId = rs.getInt(1);

                card.setCardId(cardId);

            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertCardForPassenger() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertCardForPassenger() in MySqlCardDao()");
        }

        return insertCardDetails(card);
    }

    private boolean insertCardDetails(Card card) throws DaoException
    {

        if(card.getCardAccessType().equals(CardAccessType.THREE_ZONES))
        {
            if(!card.getZones().isEmpty())
            {
                for(Zone zone : card.getZones())
                {
                    zoneDao.insertZonesForCard(card.getCardId(), zone.getZoneId());
                }

            }

        }

        if(card instanceof GreyCard)
        {
            return insertGreyCardDetails(card);
        }
        else
        {
            return insertBlueCardDetails(card);
        }
    }

    private boolean insertGreyCardDetails(Card card) throws DaoException
    {
        try
        {
            if(card instanceof GreyCard)
            {
                //Get a connection to the database
                con = this.getConnection();
                String query = "INSERT INTO grey_cards (card_id, end_datetime) VALUES\n" +
                        "(?, ?)";

                ps = con.prepareStatement(query);
                ps.setInt(1, card.getCardId());
                ps.setDate(2, java.sql.Date.valueOf(((GreyCard) card).getEndDate()));
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    return true;
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertGreyCardDetails() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertGreyCardDetails() in MySqlCardDao()");
        }

        return false;
    }

    private boolean insertBlueCardDetails(Card card) throws DaoException
    {

        try
        {
            if(card instanceof BlueCard)
            {
                //Get a connection to the database
                con = this.getConnection();
                String query = "INSERT INTO blue_cards (card_id, total_trips_allowed) VALUES\n" +
                        "(?, ?)";

                ps = con.prepareStatement(query);
                ps.setInt(1, card.getCardId());
                ps.setInt(2, ((BlueCard) card).getTotalTrips());
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    return true;
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertBlueCardDetails() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertBlueCardDetails() in MySqlCardDao()");
        }
        return false;
    }

    @Override
    protected Card createDto() throws SQLException
    {
        Card card = null;

        int cardId = rs.getInt("card_id");
        String cardType = rs.getString("card_type");
        CardAccessType accessType = enumLabelConverter.fromLabel(rs.getString("access_type"), CardAccessType.class);


        int cardPriceId = rs.getInt("card_price_id");
        double physicalCardPrice = rs.getDouble("physical_card_price");
        double topUpPrice = rs.getDouble("top_up_price");

        CardPrice cardPrice = new CardPrice(cardPriceId, physicalCardPrice, topUpPrice);

        if(cardType.equalsIgnoreCase("grey card")  || cardType.equalsIgnoreCase("student card"))
        {
            LocalDate endDate = rs.getTimestamp("end_date").toLocalDateTime().toLocalDate();

            if(cardType.equalsIgnoreCase("grey card"))
            {
                card = new GreyCard(cardId, accessType, cardPrice, endDate);
            }
            else
            {
                card = new StudentCard(cardId, accessType, cardPrice, endDate);
            }

        }
        else if(cardType.equalsIgnoreCase("blue card")  || cardType.equalsIgnoreCase("tour card"))
        {
            int numberOfTrips = rs.getInt("total_trips");
            double tripPrice = rs.getDouble("trip_price");

            if(cardType.equalsIgnoreCase("blue card"))
            {
                card = new BlueCard(cardId, accessType, cardPrice, numberOfTrips);
            }
            else
            {
                card = new TourCard(cardId, accessType, cardPrice, numberOfTrips);
            }
        }

        List<Zone> zones;

        if(card != null)
        {
            if(card.getCardAccessType().equals(CardAccessType.THREE_ZONES))
            {
                zones = zoneDao.findAllZonesByZoneId(cardId);

                for (Zone zone : zones)
                {
                    card.addZone(zone);
                }

            }
        }

        return card;
    }

    @Override
    public boolean remove(Card card) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "DELETE FROM cards WHERE card_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, card.getCardId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                query = "SELECT * FROM cards WHERE user_id = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, card.getCardId());
                rs = ps.executeQuery();

                if (!rs.next())
                {
                    return true;
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("remove() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("remove() in MySqlCardDao");
        }

        return false;
    }

}
