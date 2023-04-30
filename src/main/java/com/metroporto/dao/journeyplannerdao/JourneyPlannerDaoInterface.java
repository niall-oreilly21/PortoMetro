package com.metroporto.dao.journeyplannerdao;

import com.metroporto.JourneyPlanner;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import com.metroporto.users.User;

import java.util.List;

public interface JourneyPlannerDaoInterface
{
    List<JourneyPlanner> findAllJourneyPlannersByUserId(int userId) throws DaoException;
    boolean insertJourneyPlannerForPassenger(User user, JourneyPlanner journeyPlanner) throws DaoException;
}
