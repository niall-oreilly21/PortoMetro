package com.metroporto.dao.zonedao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;

public interface ZoneDaoInterface
{
    Zone findZoneByZoneId(int zoneId) throws DaoException;
}
