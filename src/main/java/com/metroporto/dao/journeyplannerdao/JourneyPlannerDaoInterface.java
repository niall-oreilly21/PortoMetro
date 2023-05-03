package com.metroporto.dao.journeyplannerdao;

import com.metroporto.metro.JourneyPlanner;
import com.metroporto.dao.RemoveDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.User;

import java.util.List;

public interface JourneyPlannerDaoInterface extends RemoveDaoInterface<JourneyPlanner>
{
    List<JourneyPlanner> findAllJourneyPlannersByUserId(int userId) throws DaoException;
    boolean insertJourneyPlannerForPassenger(User user, JourneyPlanner journeyPlanner) throws DaoException;
}
