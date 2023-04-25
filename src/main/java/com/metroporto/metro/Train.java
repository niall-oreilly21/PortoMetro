package com.metroporto.metro;

import com.metroporto.enums.TrainModel;

import java.util.Objects;

public class Train implements Comparable<Train>
{
    private String trainId;
    private TrainModel trainModel;
    private int carriages;
    private int capacity;

    public Train(String trainId, TrainModel trainModel, int carriages, int capacity)
    {
        this.trainId = trainId;
        this.trainModel = trainModel;
        this.carriages = carriages;
        this.capacity = capacity;
    }

    public String getTrainId()
    {
        return trainId;
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
                "trainID='" + trainId + '\'' +
                ", trainModel=" + trainModel +
                ", carriages=" + carriages +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public int compareTo(Train otherTrain)
    {
        if(this.trainModel.getLabel().equals(otherTrain.getTrainModel().getLabel()))
        {
            return this.trainId.compareToIgnoreCase(otherTrain.getTrainId());
        }
        return this.trainModel.getLabel().compareTo(otherTrain.getTrainModel().getLabel());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return carriages == train.carriages && capacity == train.capacity && Objects.equals(trainId, train.trainId) && trainModel == train.trainModel;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(trainId, trainModel, carriages, capacity);
    }
}
