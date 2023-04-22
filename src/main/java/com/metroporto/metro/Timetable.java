package com.metroporto.metro;

import com.metroporto.enums.TimeTableType;

import java.util.ArrayList;
import java.util.List;

public class Timetable
{
    private final int timetableId;
    private String scheduleDescription;
    private TimeTableType timeTableType;
    private List<List<Schedule>> timeTableSchedules;

    public Timetable(int scheduleId, String scheduleDescription, TimeTableType timeTableType)
    {
        this.timetableId = scheduleId;
        this.scheduleDescription = scheduleDescription;
        this.timeTableType = timeTableType;
        timeTableSchedules = new ArrayList<>();
    }

    public Timetable(TimeTableType timeTableType)
    {
        this.timetableId = 0;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        timeTableSchedules = new ArrayList<>();
    }
    public Timetable()
    {
        this.timetableId = 0;
        this.scheduleDescription = "";
        this.timeTableType = TimeTableType.MONDAY_TO_FRIDAY;
        timeTableSchedules = new ArrayList<>();
    }

    public void addSchedules(List<Schedule> schedules)
    {
        timeTableSchedules.add(schedules);
    }

    public int getTimetableId()
    {
        return timetableId;
    }

    public String getScheduleDescription()
    {
        return scheduleDescription;
    }

    public TimeTableType getTimeTableType()
    {
        return timeTableType;
    }

    public void setTimeTableType(TimeTableType timeTableType)
    {
        this.timeTableType = timeTableType;
    }

    @Override
    public String toString()
    {
        return "Timetable{" +
                "scheduleId=" + timetableId +
                ", scheduleDescription='" + scheduleDescription + '\'' +
                ", timeTableType=" + timeTableType +
                '}';
    }

    public void displayTimeTable()
    {
        for(List<Schedule> schedules : timeTableSchedules)
        {
            for (Schedule schedule : schedules)
            {
                System.out.print(schedule);
            }
            System.out.println();
        }
    }

    public void getSchedulesByStation(String stationName)
    {
        int index = getIndex(stationName);

        if(index == -1)
        {
            return;
        }


       List<Schedule>scheduleList = new ArrayList<>();

        for (List<Schedule> schedules : timeTableSchedules)
        {
            scheduleList.add(schedules.get(index));
        }

        System.out.println(stationName);
        for(Schedule schedule : scheduleList)
        {
            System.out.print(schedule);
        }
    }

    private int getIndex(String station)
    {
        int index = -1;
        List<Schedule> row = timeTableSchedules.get(0);

        for (int i = 0; i < row.size(); i++)
        {
                if(row.get(i).getStation().getStationName().equalsIgnoreCase(station))
                {
                    index = i;
                    break;
                }

        }

        return index;
    }
}
