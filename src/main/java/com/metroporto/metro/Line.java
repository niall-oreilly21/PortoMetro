package com.metroporto.metro;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
    private final String lineId;
    private Station startStation;
    private Station endStation;
    private String lineName;
    private List<Station> stationStops;
    private List<Train> trains;

    public Line(String lineId, Station startStation, Station endStation, String lineName, List<Station> stationStops, List<Train> trains)
    {
        this.lineId = lineId;
        this.startStation = startStation;
        this.endStation = endStation;
        this.lineName = lineName;
        this.stationStops = stationStops;
        this.trains = trains;
    }

    public Line(String lineId, Station startStation, Station endStation, String lineName, List<Station> stations)
    {
        this.lineId = lineId;
        this.startStation = startStation;
        this.endStation = endStation;
        this.lineName = lineName;
        this.stationStops = stations;
        this.trains = new ArrayList<>();
    }

    public String getLineId()
    {
        return lineId;
    }

    public Station getStartStation()
    {
        return startStation;
    }

    public Station getEndStation()
    {
        return endStation;
    }

    public String getLineName()
    {
        return lineName;
    }

    public List<Station> getStationStops()
    {
        return stationStops;
    }

    public List<Train> getTrains()
    {
        return trains;
    }

    public void addTrain(Train train)
    {
        trains.add(train);
    }


    @Override
    public int compareTo(Line otherLine)
    {
        return this.lineId.compareToIgnoreCase(otherLine.getLineId());
    }
}
