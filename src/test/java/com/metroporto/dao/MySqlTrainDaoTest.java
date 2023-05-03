package com.metroporto.dao;

import com.metroporto.dao.traindao.MySqlTrainDao;
import com.metroporto.dao.traindao.TrainDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MySqlTrainDaoTest
{
    private TrainDaoInterface trainDao;

    @BeforeEach
    void setUp()
    {
        trainDao = new MySqlTrainDao();
    }

    @Test
    void findAllTrainsByLineIdAssertFalse() throws DaoException
    {
        System.out.println("\nfindAllTrainsByLine() does not return a empty List for line A.");
        List<Train> trains = trainDao.findAllTrainsByLineId("A");
        assertFalse(trains.isEmpty());
    }

    @Test
    void findAllTrainsByLineIdAssertTrue() throws DaoException
    {
        System.out.println("\nfindAllTrainsByLine() returns a empty List for line M.");
        List<Train> trains = trainDao.findAllTrainsByLineId("M");
        assertTrue(trains.isEmpty());
    }

    @Test
    void findTrainByTrainIdNotNull() throws DaoException
    {
        System.out.println("\nfindTrainByTrainId() returns not null when the TrainId = FB98 (is in database).");
        String trainId = "FB98";
        Train train = trainDao.findTrainByTrainId(trainId);
        assertNotNull(train);
    }

    @Test
    void findTrainByTrainIdNull() throws DaoException
    {
        System.out.println("\nfindTrainByTrainId() returns null when the TrainId = FB92 (is not in database).");
        String trainId = "FB92";
        Train train = trainDao.findTrainByTrainId(trainId);
        assertNull(train);
    }

}
