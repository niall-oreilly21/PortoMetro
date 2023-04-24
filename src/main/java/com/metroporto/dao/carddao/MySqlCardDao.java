package com.metroporto.dao.carddao;

import com.metroporto.cards.BlueCard;
import com.metroporto.cards.Card;
import com.metroporto.cards.GreyCard;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MySqlCardDao extends MySqlDao<Card> implements CardDaoInterface
{
    private ZoneDaoInterface zoneDao;

    public MySqlCardDao()
    {
        zoneDao = new MySqlZoneDao();
    }

    @Override
    public Card findCardByCardId(int cardIdToBeFound) throws DaoException
    {
        Card card = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM all_cards WHERE card_id = ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, cardIdToBeFound);

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
    protected Card createDto() throws SQLException
    {
        Card card = null;

        int cardId = rs.getInt("card_id");
        String cardType = rs.getString("card_type");
        CardAccessType accessType = enumLabelConverter.fromLabel(rs.getString("access_type"), CardAccessType.class);
        double cardPrice = rs.getDouble("card_price");

        LocalDateTime endDateTime = rs.getTimestamp("end_datetime").toLocalDateTime();
        switch (cardType.toLowerCase())
        {
            case "blue card":
                int numberOfTrips = rs.getInt("total_trips_allowed");
                card = new BlueCard(cardId, accessType, cardPrice, numberOfTrips);
                break;

            case "grey card":
                card = new GreyCard(cardId, accessType, cardPrice, endDateTime);
                break;

            case "student card":
                card = new GreyCard(cardId, accessType, cardPrice, endDateTime);
                break;

            case "tour card":
                card = new GreyCard(cardId, accessType, cardPrice, endDateTime);
                break;

            default:
                break;
        }

        List<Zone> zones;

        switch (accessType)
        {
            case THREE_ZONES:
                zones = zoneDao.findAllZonesByZoneId(cardId);

                for(Zone zone : zones)
                {
                    if (card != null)
                    {
                        card.addZone(zone);
                    }
                }
                break;

            case ALL_ZONES:
                break;
        }

        return card;
    }
}
