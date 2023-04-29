package com.metroporto;

import com.metroporto.metro.Schedule;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

public class ComparatorSchedulesClosestTime implements Comparator<Schedule>
{
    private LocalTime startTime;

    public ComparatorSchedulesClosestTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    @Override
    public int compare(Schedule scheduleOne, Schedule scheduleTwo)
    {
        LocalTime scheduleOneTime = scheduleOne.getDepartureTime();
        LocalTime scheduleTwoTime = scheduleTwo.getDepartureTime();

        Duration scheduleOneTimeDiff = Duration.between(startTime, scheduleOneTime);
        Duration scheduleTwoTimeDiff = Duration.between(startTime, scheduleTwoTime);

        if (scheduleOneTime.equals(startTime))
        {
            return -1;
        }
        else if (scheduleTwoTime.equals(startTime))
        {
            return 1;
        }
        else
        {
            return scheduleOneTimeDiff.compareTo(scheduleTwoTimeDiff);
        }
    }
}
