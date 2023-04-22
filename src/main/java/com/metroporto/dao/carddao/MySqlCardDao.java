package com.metroporto.dao.carddao;

import com.metroporto.cards.BlueCard;
import com.metroporto.cards.Card;
import com.metroporto.cards.GreyCard;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.metro.Zone;
import com.metroporto.users.Administrator;
import com.metroporto.users.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.metroporto.enums.CardAccessType.*;

public class MySqlCardDao extends MySqlDao implements CardDaoInterface
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
                int cardId = rs.getInt("card_id");
                String cardType = rs.getString("card_type");

                String accessTypeString = rs.getString("access_type");
                CardAccessType accessType = enumLabelConverter.fromLabel(accessTypeString, CardAccessType.class);

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
                            card.addZone(zone);
                        }
                        break;

                    case ALL_ZONES:
                        break;
                }
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findCardByCardId() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findCardByCardId()");
        }

        return card;
    }
}