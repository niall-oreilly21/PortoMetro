package com.metroporto.dao.carddao;

import com.metroporto.cards.BlueCard;
import com.metroporto.cards.Card;
import com.metroporto.dao.MySqlDao;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.metro.Zone;
import com.metroporto.users.Administrator;
import com.metroporto.users.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class MySqlCardDao extends MySqlDao implements CardDaoInterface
{
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

                CardAccessType accessType = enumLabelConverter.fromLabel(cardType, CardAccessType.class);

                double cardPrice = rs.getDouble("card_price");

                LocalDateTime endDateTime = rs.getTimestamp("end_datetime").toLocalDateTime();
                int zoneId = rs.getInt("zone_id");


//                if(accessType.equalsIgnoreCase("3 zones"))
//                {
//                    int cardId = rs.getInt(" passengers.card_id");
//
//                    Card card = cardDao.findCardByCardId(cardId);
//
//                    if(userType.equalsIgnoreCase("student"))
//                    {
//                        String universityId = rs.getString("students_universities.university_id");
//
//                        University university = universityDao.findUniversityByUniversityId(universityId);
//
//                        users.add(new Student(userId, email, password, card, university));
//                    }
//
//                }
//
//                if(cardType.equalsIgnoreCase("blue card"))
//                {
//                    int numberOfTrips = rs.getInt("total_trips_allowed");
//                    card = new BlueCard(cardId, AC, cardPrice, numberOfTrips);
//                    users.add(new Administrator(userId, email, password));
//                }
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
