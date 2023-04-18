package com.metroporto.dao.facilitydao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;

import java.util.List;

public interface FacilityDaoInterface
{
    List<Facility> findAllFacilitiesByStationName(String stationName) throws DaoException;

}
