package com.metroporto.metro;

import com.metroporto.enums.TimeTableType;

import java.time.LocalTime;

public class Schedule
{
    private Station station;
    private LocalTime departureTime;

    public Schedule(Station station, LocalTime departureTime)
    {
        this.station = station;
        this.departureTime = departureTime;
    }

    public Station getStation()
    {
        return station;
    }

    public LocalTime getDepartureTime()
    {
        return departureTime;
    }

    @Override
    public String toString()
    {
        return  departureTime.toString() + ", ";
    }
//    @Override
//    public int compareTo(Schedule otherSchedule)
//    {
//        if(this.arrivalTime.equals(otherSchedule.getArrivalTime()))
//        {
//            return this.scheduleId.compareToIgnoreCase(otherSchedule.getScheduleId());
//        }
//
//        return this.arrivalTime.compareTo(otherSchedule.getArrivalTime());
//    }
}
