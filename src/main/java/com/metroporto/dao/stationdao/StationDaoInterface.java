package com.metroporto.dao.stationdao;

import com.google.gson.Gson;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;

import java.util.List;

public interface StationDaoInterface
{
    Gson gsonParser = new Gson();
    List<Station> findAllStations() throws DaoException;
    Station findStationByStationId(String stationId) throws DaoException;
}
