package com.metroporto.metro;

import java.util.*;

public class Line implements Comparable<Line>
{
    private final String lineId;
    private String lineName;
    private List<Route> routes;
    private List<Train> trains;
    private List<Station> stations;

    public void setStations(List<Station> stations)
    {
        this.stations = stations;
    }

    public List<Station> getStations()
    {
        return stations;
    }

    public Line(Line line)
    {
        this.lineId = line.getLineId();
        this.lineName = line.getLineName();
        this.routes = line.getRoutes();
        this.trains = line.getTrains();
        stations = line.getStations();
    }
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(lineId, line.lineId) && Objects.equals(lineName, line.lineName) && Objects.equals(routes, line.routes) && Objects.equals(trains, line.trains) && Objects.equals(stations, line.stations);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(lineId, lineName, routes, trains, stations);
    }
}
