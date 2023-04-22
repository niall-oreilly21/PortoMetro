package com.metroporto.metro;

import java.util.ArrayList;
import java.util.List;

public class Route
{
    private final int routeId;
    private List<Timetable> timetables;
    private Station endStation;

    public Route(int routeId, Station endStation)
    {
        this.routeId = routeId;
        this.endStation = endStation;
        timetables = new ArrayList<>();
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
