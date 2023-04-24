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

    public Line(String lineId, String lineName, List<Route> routes, List<Train> trains)
    {
        this.lineId = lineId;
        this.lineName = lineName;
        this.routes = routes;
        this.trains = trains;
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

    public List<Route> getRoutes()
    {
        return routes;
    }

    public void addTrain(Train train)
    {
        trains.add(train);
    }

    @Override
    public String toString()
    {
        return "Line{" +
                "lineId='" + lineId + '\'' +
                ", lineName='" + lineName + '\'' +
                ", routes=" + routes +
                ", trains=" + trains +
                '}';
    }

    @Override
    public int compareTo(Line otherLine)
    {
        return this.lineId.compareToIgnoreCase(otherLine.getLineId());
    }
}
