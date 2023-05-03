package com.metroporto.dao.carddao;

import com.metroporto.cards.*;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;
import com.metroporto.users.Passenger;
import com.metroporto.users.User;

import java.sql.SQLException;
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
            query = "SELECT * FROM all_cards;";

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
    public Card findCardByUserId(int userId) throws DaoException
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
    public CardPrice findCardPriceForCard(Card card) throws DaoException
    {
        CardPrice cardPrice = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM cards_prices WHERE card_type = ? AND access_type = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, getCardType(card));
            ps.setString(2, card.getCardAccessType().getLabel());

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                cardPrice = createCardPriceDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findCardPriceByAccessType() in MySqlCardDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findCardPriceByAccessType() in MySqlCardDao");
        }

        return cardPrice;
    }

    private String getCardType(Card card)
    {
        String cardType;

        if (card instanceof GreyCard)
        {
            if (card instanceof StudentCard)
            {
                cardType = "student card";
            } else
            {
                cardType = "grey card";
            }
        }
        else
        {
            if (card instanceof TourCard)
            {
                cardType = "tour card";
            } else
            {
                cardType = "blue card";
            }
        }

        return cardType;
    }
    @Override
    public boolean insertCardForPassenger(User user) throws DaoException
    {
        Card card = null;

        try
        {
            if(user instanceof Passenger)
            {
                int maxNumber = findMaxCardId();

                String prefix = "MTRP2023";
                int nextNumber = maxNumber + 1;

                //Get a connection to the database
                con = this.getConnection();
                query = "INSERT INTO cards (card_id, user_id, card_price_id) VALUES\n" +
                        "(?, ?, ?)";

                ps = con.prepareStatement(query);

                String cardType;

                card = ((Passenger) user).getMetroCard();


                String cardId = prefix + String.format("%05d", nextNumber);

                card.setCardId(cardId);

                ps.setString(1, card.getCardId());
                ps.setInt(2, user.getUserId());
                ps.setInt(3, card.getCardPrice().getCardPriceId());
                ps.executeUpdate();
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
                String query = "INSERT INTO grey_cards (card_id, card_start_date, card_end_date) VALUES\n" +
                        "(?, ?, ?)";

                ps = con.prepareStatement(query);
                ps.setString(1, card.getCardId());
                ps.setDate(2, java.sql.Date.valueOf(((GreyCard) card).getStartDate()));
                ps.setDate(3, java.sql.Date.valueOf(((GreyCard) card).getEndDate()));
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
                String query = "INSERT INTO blue_cards (card_id, total_trips) VALUES\n" +
                        "(?, ?)";

                ps = con.prepareStatement(query);
                ps.setString(1, card.getCardId());
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

    private int findMaxCardId() throws DaoException
    {
        int maxCardId = 0;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT MAX(SUBSTRING(card_id, 11)) as max_number FROM cards WHERE card_id LIKE 'MTRP2023%';\n;";

            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                maxCardId = rs.getInt("max_number");
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("findMaxCardId() in MySqlCardDao" + sqe.getMessage());
        }
        finally
        {
            handleFinally("findMaxCardId() in MySqlCardDao");
        }

        return maxCardId;
    }

    private CardPrice createCardPriceDto() throws SQLException
    {
        int cardPriceId = rs.getInt("card_price_id");
        double physicalCardPrice = rs.getDouble("physical_card_price");
        double topUpPrice = rs.getDouble("top_up_price");

        return new CardPrice(cardPriceId, physicalCardPrice, topUpPrice);
    }
    @Override
    protected Card createDto() throws SQLException
    {
        Card card = null;

        String cardId = rs.getString("card_id");
        String cardType = rs.getString("card_type");
        CardAccessType accessType = enumLabelConverter.fromLabel(rs.getString("access_type"), CardAccessType.class);
        CardPrice cardPrice = createCardPriceDto();

        if(cardType.equalsIgnoreCase("grey card")  || cardType.equalsIgnoreCase("student card"))
        {
            LocalDate startDate = rs.getTimestamp("card_start_date").toLocalDateTime().toLocalDate();
            LocalDate endDate = rs.getTimestamp("card_end_date").toLocalDateTime().toLocalDate();

            if(cardType.equalsIgnoreCase("grey card"))
            {
                card = new GreyCard(cardId, accessType, cardPrice, startDate, endDate);
            }
            else
            {
                card = new StudentCard(cardId, accessType, cardPrice, startDate, endDate);
            }
        }
        else if(cardType.equalsIgnoreCase("blue card")  || cardType.equalsIgnoreCase("tour card"))
        {
            int numberOfTrips = rs.getInt("total_trips");

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
                card.setZones(zones);
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
            ps.setString(1, card.getCardId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                query = "SELECT * FROM cards WHERE user_id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, card.getCardId());
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
