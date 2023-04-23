package com.metroporto.metro;

import java.util.ArrayList;
import java.util.List;

public class Route
{
    private final int routeId;
    private Station endStation;
    private List<Timetable> timetables;

    public Route(int routeId, Station endStation)
    {
        this.routeId = routeId;
        this.endStation = endStation;
        timetables = new ArrayList<>();
    }

    public Route(int routeId, Station endStation, List<Timetable> timetables)
    {
        this.routeId = routeId;
        this.endStation = endStation;
        this.timetables = timetables;
    }

    public void addTimeTable(Timetable timetable)
    {
        timetables.add(timetable);
    }

    public int getRouteId()
    {
        return routeId;
    }

    public List<Timetable> getTimetables()
    {
        return timetables;
    }

    public Station getEndStation()
    {
        return endStation;
    }

    @Override
    public String toString()
    {
        return "Route{" +
                "routeId=" + routeId +
                ", timetables=" + timetables +
                ", endStation=" + endStation +
                '}';
    }

}
