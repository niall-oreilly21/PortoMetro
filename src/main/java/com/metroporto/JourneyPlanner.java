package com.metroporto;

import com.metroporto.createdatabase.importtimetables.StartInterface;
import com.metroporto.enums.TimeTableType;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JourneyPlanner implements StartInterface
{
    private List<JourneyRoute> journeyRoutes;
    private Station startStation;
    private Station endStation;
    private LocalTime startTime;
    private TimeTableType timetableType;
    private MetroSystem metroSystem;

    public JourneyPlanner(Station startStation, Station endStation, LocalTime startTime, TimeTableType timetableType)
    {
        this.journeyRoutes = new ArrayList<>();
        this.startStation = startStation;
        this.endStation = endStation;
        metroSystem = null;
        this.startTime = startTime;
        this.timetableType = timetableType;
    }

    public JourneyPlanner()
    {
        this.journeyRoutes = new ArrayList<>();
        this.startStation = null;
        this.endStation = null;
        metroSystem = null;
        this.startTime = null;
        this.timetableType = TimeTableType.MONDAY_TO_FRIDAY;
    }

    public List<JourneyRoute> getJourneyRoutes()
    {
        return journeyRoutes;
    }

    public void setJourneyRoutes(List<JourneyRoute> journeyRoutes)
    {
        this.journeyRoutes = journeyRoutes;
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

    public void setMetroSystem(MetroSystem metroSystem)
    {
        this.metroSystem = metroSystem;
    }

    @Override
    public void start()
    {
        if(!this.journeyRoutes.isEmpty())
        {
            this.journeyRoutes.clear();
        }
        if(metroSystem != null)
        {
            journeyRoutes.addAll(metroSystem.findShortestPath(startStation, endStation, startTime, timetableType));
        }
    }

    public void displayJourneyPlanner()
    {
        for(JourneyRoute journeyRoute : journeyRoutes)
        {
            int i = 0;
            System.out.println("-----LINE: " +journeyRoute.getLine().getLineName() + "-----");
            System.out.println("-----ROUTE: " + journeyRoute.getRoute().getEndStation().getStationName() + "-----");

            for (Schedule schedule : journeyRoute.getSchedules())
            {
                if (schedule instanceof ConnectionSchedule)
                {
                    if (i != 0)
                    {
                        System.out.println("------Connection: " + schedule.getStation().getStationName() + "------");
                        System.out.println("Arrival time: " + ((ConnectionSchedule) schedule).getArrivalTime());
                        System.out.println("Transfer time: " + ((ConnectionSchedule) schedule).getTransferTime() + " mins");
                    }
                    else
                    {
                        System.out.println("Departure time: " + (schedule.getDepartureTime()));
                    }
                }
                else
                {
                    System.out.println("Stop: " + schedule.getStation().getStationName());
                    System.out.println("Departure time: " + schedule.getDepartureTime());
                }
                System.out.println();
                i++;
            }
        }
    }

}
