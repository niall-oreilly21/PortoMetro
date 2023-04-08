package com.metroporto.metro;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
    private final String lineId;
    private String lineName;
    private List<Route> routes;
    private List<Train> trains;
    private List<Station> stations;

    public Line(String lineId, String lineName)
    {
        this.lineId = lineId;
        this.lineName = lineName;
        routes = new ArrayList<>(2);
        trains = new ArrayList<>();
        stations = new ArrayList<>();
    }

    public String getLineId()
    {
        return lineId;
    }

    public String getLineName()
    {
        return lineName;
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
