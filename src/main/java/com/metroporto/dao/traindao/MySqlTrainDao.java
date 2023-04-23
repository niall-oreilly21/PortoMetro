package com.metroporto.dao.traindao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.enums.TrainModel;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Train;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTrainDao extends MySqlDao<Train> implements TrainDaoInterface
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
                trains.add(createDto());
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

    @Override
    protected Train createDto() throws SQLException
    {
        String trainId = rs.getString("train_id");
        TrainModel trainModel = enumLabelConverter.fromLabel(rs.getString("train_model"), TrainModel.class);

        int carriages = rs.getInt("carriages");
        int capacity = rs.getInt("capacity");

        return new Train(trainId, trainModel, carriages, capacity);
    }
}
