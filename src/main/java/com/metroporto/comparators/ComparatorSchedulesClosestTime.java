package com.metroporto.comparators;

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

        // If the departure time is before the current time, add one day to the difference
        if (scheduleOneTime.isBefore(startTime))
        {
            scheduleOneTimeDiff = scheduleOneTimeDiff.plusDays(1);
        }
        if (scheduleTwoTime.isBefore(startTime))
        {
            scheduleTwoTimeDiff = scheduleTwoTimeDiff.plusDays(1);
        }

        // Compare the differences between departure times and the current time
        return scheduleOneTimeDiff.compareTo(scheduleTwoTimeDiff);
    }
}
