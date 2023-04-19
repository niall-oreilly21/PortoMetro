package com.metroporto.dao.traindao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.enums.TrainModel;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTrainDao extends MySqlDao implements TrainDaoInterface
{
    @Override
    public List<Train> findAllTrainsByLineId(String lineId) throws DaoException
    {
        List<Train> trains = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();

            query =
                    "SELECT * FROM trains \n" +
                            "WHERE line_id = ?;";

            ps = con.prepareStatement(query);
            ps.setString(1, lineId);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                String trainId = rs.getString("train_id");
                String trainModelString = rs.getString("train_model");
                TrainModel trainModel;

                if(trainModelString.equalsIgnoreCase(TrainModel.EURO_TRAM.getLabel()))
                {
                    trainModel = TrainModel.EURO_TRAM;
                }
                else
                {
                    trainModel = TrainModel.LRVs;
                }

                int carriages = rs.getInt("carriages");
                int capacity = rs.getInt("capacity");

                Train train = new Train(trainId, trainModel, carriages, capacity);

                trains.add(train);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllRoutesByLineId() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllRoutesByLineId()");
        }

        return trains;
    }
}
