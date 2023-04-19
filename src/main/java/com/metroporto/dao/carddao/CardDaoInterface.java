package com.metroporto.dao.carddao;

import com.metroporto.cards.Card;
import com.metroporto.exceptions.DaoException;

public interface CardDaoInterface
{
    Card findCardByCardId(int cardId) throws DaoException;
}
