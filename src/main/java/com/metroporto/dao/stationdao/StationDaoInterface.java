package com.metroporto.dao.stationdao;

import com.google.gson.Gson;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;

import java.util.List;

public interface StationDaoInterface
{
    Gson gsonParser = new Gson();
    public List<Station> findAllStations() throws DaoException;
}
