package com.metroporto.dao.carddao;

import com.metroporto.cards.Card;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

public interface CardDaoInterface
{
    Card findCardByCardId(int cardId) throws DaoException;
    void insertCardForPassenger(User user) throws DaoException;
}
