package com.metroporto.dao.traindao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;

import java.util.List;

public interface TrainDaoInterface extends FindAllDaoInterface<Train>
{
    Train findTrainByTrainId(String trainId) throws DaoException;
    List<Train> findAllTrainsByLineId(String lineId) throws DaoException;
}
