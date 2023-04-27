package com.metroporto;

import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;

import java.time.Duration;
import java.time.LocalTime;

public class ConnectionSchedule extends Schedule
{
    private LocalTime arrivalTime;
    private int transferTime;

    public ConnectionSchedule(Station station, LocalTime arrivalTime, LocalTime departureTime)
    {
        super(station, departureTime);
        this.arrivalTime = arrivalTime;
        setTransferTime();
    }

    private void setTransferTime()
    {
        Duration transferDuration = Duration.between(arrivalTime, departureTime);
        this.transferTime = (int) transferDuration.toMinutes();
    }

    public LocalTime getArrivalTime()
    {
        return arrivalTime;
    }

    public int getTransferTime()
    {
        return transferTime;
    }

}
