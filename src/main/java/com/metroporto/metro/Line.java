package com.metroporto.metro;

import java.util.*;

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
        setStations();
    }

    private void setStations()
    {
         stations = new ArrayList<>(routes.get(0).getTimetables().get(0).getStations());
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

    public List<Station> getStations()
    {
        return stations;
    }

    public Route findRoute(Station startStation, Station endStation)
    {
        Station lastStation = stations.get(stations.size() - 1); // Get the last station in the list
        List<Station> tempStations;

        for (Route route : routes)
        {
            if (route.getEndStation().equals(lastStation))
            {
                // The last station matches the end station, use the original stations list
                tempStations = stations;
            }
            else
            {
                // The last station does not match the end station, use the reversed stations list
                tempStations = new ArrayList<>(stations); // Create a reversed copy of the stations list
                Collections.reverse(tempStations); // Reverse the stations list
            }

            int startIndex = tempStations.indexOf(startStation);
            int endIndex = tempStations.indexOf(endStation);

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex)
            {
                return route;
            }
        }
        return null; // return null if no route is found
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
