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

        // If the departure time is before the start time, make the difference negative
        if (scheduleOneTime.isBefore(startTime))
        {
            scheduleOneTimeDiff = scheduleOneTimeDiff.negated();
        }
        if (scheduleTwoTime.isBefore(startTime))
        {
            scheduleTwoTimeDiff = scheduleTwoTimeDiff.negated();
        }

        // Compare the differences between departure times and start time
        return scheduleOneTimeDiff.compareTo(scheduleTwoTimeDiff);
    }
}
