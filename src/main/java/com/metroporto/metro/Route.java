package com.metroporto.metro;

import com.metroporto.enums.TimetableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Timetable getTimetableByTimetableType(TimetableType timeTableType)
    {
        for (Timetable timetable : timetables)
        {
            if (timetable.getTimeTableType().equals(timeTableType))
            {
                return timetable;
            }
        }
        return null;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return routeId == route.routeId && Objects.equals(endStation, route.endStation) && Objects.equals(timetables, route.timetables);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(routeId, endStation, timetables);
    }
}
