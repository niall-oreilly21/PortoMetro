package com.metroporto.dao.traindao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;

import java.util.List;

public interface TrainDaoInterface
{
    List<Train> findAllTrainsByLineId(String lineId) throws DaoException;
}
