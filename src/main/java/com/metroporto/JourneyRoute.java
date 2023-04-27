package com.metroporto;

import com.metroporto.metro.Line;
import com.metroporto.metro.Route;
import com.metroporto.metro.Schedule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class JourneyRoute
{
    private Line line;
    private Route route;
    private List<Schedule> schedules;

    public JourneyRoute(Line line, Route route)
    {
        this.line = line;
        this.route = route;
        this.schedules = new ArrayList<>();
    }

    public Line getLine()
    {
        return line;
    }

    public void setLine(Line line)
    {
        this.line = line;
    }

    public Route getRoute()
    {
        return route;
    }

    public void setRoute(Route route)
    {
        this.route = route;
    }

    public List<Schedule> getSchedules()
    {
        return schedules;
    }

    public void addSchedule(Schedule schedule)
    {
        schedules.add(schedule);
    }

    @Override
    public String toString()
    {
        return "JourneyRoute" +
                "{" +
                "line=" + line +
                ", route=" + route +
                ", schedulesOnLine=" + schedules +
                '}';
    }
}
