package com.metroporto.dao;

import com.metroporto.cards.BlueCard;
import com.metroporto.cards.Card;
import com.metroporto.cards.CardPrice;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Line;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MySqlCardDaoTest
{
    private CardDaoInterface cardDao;
    private Passenger user;
    private Card card;

    @BeforeEach
    void setUp()
    {
        cardDao = new MySqlCardDao();
        user = new Passenger("johndoe@gmail.com", "John", "Doe");
        card = new BlueCard(8);
        user.setMetroCard(card);
    }

    @Test
    void findCardPriceForCardNotNull() throws DaoException
    {
        System.out.println("\nfindCardPriceForCard() returns not null for valid access type.");

        user.getMetroCard().setAccessType(CardAccessType.ALL_ZONES);
        CardPrice cardPrice = cardDao.findCardPriceForCard(user.getMetroCard());
        assertNotNull(cardPrice);
    }

    @Test
    void findCardPriceForCardNull() throws DaoException
    {
        System.out.println("\nfindCardPriceForCard() returns null for null access type.");
        CardPrice cardPrice = cardDao.findCardPriceForCard(user.getMetroCard());
        assertNotNull(cardPrice);
    }
}
