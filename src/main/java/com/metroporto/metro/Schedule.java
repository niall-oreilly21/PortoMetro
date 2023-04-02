package com.metroporto.metro;

import java.time.LocalDateTime;

public class Schedule implements Comparable<Schedule>
{
    private final String scheduleId;
    private Train train;
    private Station station;
    private LocalDateTime arrivalTime;

    public Schedule(String scheduleId, Train train, Station station, LocalDateTime arrivalTime)
    {
        this.scheduleId = scheduleId;
        this.train = train;
        this.station = station;
        this.arrivalTime = arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public String getScheduleId()
    {
        return scheduleId;
    }

    public Train getTrain()
    {
        return train;
    }

    public Station getStation()
    {
        return station;
    }

    public LocalDateTime getArrivalTime()
    {
        return arrivalTime;
    }

    @Override
    public String toString()
    {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", train=" + train +
                ", station=" + station +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    @Override
    public int compareTo(Schedule otherSchedule)
    {
        if(this.arrivalTime.equals(otherSchedule.getArrivalTime()))
        {
            return this.scheduleId.compareToIgnoreCase(otherSchedule.getScheduleId());
        }

        return this.arrivalTime.compareTo(otherSchedule.getArrivalTime());
    }
}
