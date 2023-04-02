package com.metroporto.metro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JourneyRoute implements Comparable<JourneyRoute>
{
    private int journeyRouteId;
    private Station startStation;
    private Station endStation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double fare;
    private String journeyRouteDescription;
    private List<Schedule> stops;

    public JourneyRoute(Station startStation, Station endStation)
    {
        journeyRouteId = 0;
        this.startStation = startStation;
        this.endStation = endStation;
        startTime = LocalDateTime.of(0 , 0, 0,0, 0);
        endTime = LocalDateTime.of(0 , 0, 0,0, 0);
        journeyRouteDescription = createJourneyRouteDescription();
        fare = 0.0;
        stops = new ArrayList<>();
    }

    public int getJourneyRouteId()
    {
        return journeyRouteId;
    }

    public void setJourneyRouteId(int journeyRouteId)
    {
        this.journeyRouteId = journeyRouteId;
    }

    public Station getStartStation()
    {
        return startStation;
    }

    public void setStartStation(Station startStation)
    {
        this.startStation = startStation;
    }

    public Station getEndStation()
    {
        return endStation;
    }

    public void setEndStation(Station endStation)
    {
        this.endStation = endStation;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }

    public double getFare()
    {
        return fare;
    }

    public void setFare(double fare)
    {
        this.fare = fare;
    }

    public String getJourneyRouteDescription()
    {
        return journeyRouteDescription;
    }

    public void setJourneyRouteDescription(String journeyRouteDescription)
    {
        this.journeyRouteDescription = journeyRouteDescription;
    }

    public List<Schedule> getStops()
    {
        return stops;
    }

    public void setStops(List<Schedule> stops)
    {
        this.stops = stops;
    }

    public String createJourneyRouteDescription()
    {
        return startStation.getStationName() + " to " + endStation.getStationName();
    }

    @Override
    public String toString()
    {
        return "JourneyRoute{" +
                "journeyRouteId=" + journeyRouteId +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", fare=" + fare +
                ", journeyRouteDescription='" + journeyRouteDescription + '\'' +
                ", stops=" + stops +
                '}';
    }

    @Override
    public int compareTo(JourneyRoute otherJourneyRoute)
    {
        return this.journeyRouteId - otherJourneyRoute.getJourneyRouteId();
    }
}
