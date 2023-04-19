package com.metroporto.dao.carddao;

import com.metroporto.cards.Card;
import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.sql.SQLException;

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
            query = "SELECT * FROM cards WHERE card_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, cardIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                int cardId = rs.getInt("card_id");
                String cardType = rs.getString("card_type");
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
