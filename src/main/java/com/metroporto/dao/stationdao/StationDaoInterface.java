package com.metroporto.dao.stationdao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;

public interface StationDaoInterface extends FindAllDaoInterface<Station>
{
    Station findStationByStationId(String stationId) throws DaoException;
}
