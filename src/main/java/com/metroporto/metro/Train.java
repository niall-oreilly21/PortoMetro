package com.metroporto.metro;

import com.metroporto.enums.TrainModel;

public class Train implements Comparable<Train>
{
    private String trainID;
    private String lineID;
    private TrainModel trainModel;
    private int carriages;
    private int capacity;

    public Train(String trainID, String lineID, TrainModel trainModel, int carriages, int capacity)
    {
        this.trainID = trainID;
        this.lineID = lineID;
        this.trainModel = trainModel;
        this.carriages = carriages;
        this.capacity = capacity;
    }

    public String getTrainID()
    {
        return trainID;
    }

    public String getLineID()
    {
        return lineID;
    }

    public TrainModel getTrainModel()
    {
        return trainModel;
    }

    public int getCarriages()
    {
        return carriages;
    }

    public int getCapacity()
    {
        return capacity;
    }

    @Override
    public String toString()
    {
        return "Train{" +
                "trainID='" + trainID + '\'' +
                ", lineID='" + lineID + '\'' +
                ", trainModel=" + trainModel +
                ", carriages=" + carriages +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public int compareTo(Train otherTrain)
    {
        if(this.lineID.equalsIgnoreCase(otherTrain.getLineID()))
        {
            return this.trainID.compareToIgnoreCase(otherTrain.getTrainID());
        }
        return this.lineID.compareToIgnoreCase(otherTrain.getLineID());
    }
}
