package com.metroporto;

import com.metroporto.metro.Schedule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JourneyPlanner
{
    List<JourneyRoute> journeyRoutes;

    public JourneyPlanner()
    {
        this.journeyRoutes = new ArrayList<>();
    }

    public void displayJourneyRoute()
    {
        for(JourneyRoute journeyRoute : journeyRoutes)
        {
            System.out.println("LINE: " +journeyRoute.getLine().getLineName());
            System.out.println("ROUTE: " + journeyRoute.getRoute().getEndStation().getStationName());

            for (Schedule schedule : journeyRoute.getSchedules())
            {
                if(schedule instanceof ConnectionSchedule)
                {
                    //if(schedules)
                }
                System.out.println(schedule.getDepartureTime());
            }
        }
    }
}
