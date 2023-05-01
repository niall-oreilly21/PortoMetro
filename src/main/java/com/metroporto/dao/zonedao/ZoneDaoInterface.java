package com.metroporto.dao.zonedao;

import com.metroporto.cards.Card;
import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.util.List;

public interface ZoneDaoInterface extends FindAllDaoInterface<Zone>
{
    Zone findZoneByZoneId(int zoneId) throws DaoException;
    List<Zone> findAllZonesByZoneId(String cardId) throws DaoException;
    void insertZonesForCard(String cardId, int zoneId) throws DaoException;
}
