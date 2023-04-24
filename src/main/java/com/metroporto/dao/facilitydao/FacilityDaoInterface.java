package com.metroporto.dao.facilitydao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;

import java.util.List;

public interface FacilityDaoInterface extends FindAllDaoInterface<Facility>
{
    List<Facility> findAllFacilitiesByStationName(String stationName) throws DaoException;

}
