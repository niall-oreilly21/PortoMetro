package com.metroporto.metro;

import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;

import java.time.Duration;
import java.time.LocalTime;

public class ConnectionSchedule extends Schedule
{
    private LocalTime arrivalTime;
    private int transferTime;

    public ConnectionSchedule(Station station, LocalTime arrivalTime)
    {
        super(station);
        this.arrivalTime = arrivalTime;
        transferTime = 0;

    }

    public void setTransferTime(int transferTime)
    {
        this.transferTime = transferTime;
    }

    private void createTransferTime()
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
        if(this.departureTime != null)
        {
            createTransferTime();
            return transferTime;
        }
        return -1;
    }

}
