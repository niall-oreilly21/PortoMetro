package com.metroporto.dao.zonedao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

import java.util.List;

public interface ZoneDaoInterface
{
    Zone findZoneByZoneId(int zoneId) throws DaoException;
    List<Zone> findAllZonesByZoneId(int cardId) throws DaoException;
}
