package com.metroporto.metro;

import com.metroporto.enums.TimeTableType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Schedule implements Comparable<Schedule>
{
    private Station station;
    private LocalTime departureTime;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

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
        return  formatter.format(departureTime) + ", ";
    }

    @Override
    public int compareTo(Schedule otherSchedule)
    {
        return departureTime.compareTo(otherSchedule.getDepartureTime());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(station, schedule.station) && Objects.equals(departureTime, schedule.departureTime);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(station, departureTime);
    }
}
