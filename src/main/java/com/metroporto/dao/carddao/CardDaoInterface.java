package com.metroporto.dao.carddao;

import com.metroporto.cards.Card;
import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.dao.RemoveDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

public interface CardDaoInterface extends FindAllDaoInterface<Card>, RemoveDaoInterface<Card>
{
    Card findCardByCardId(int userId) throws DaoException;
    boolean insertCardForPassenger(User user) throws DaoException;
}
