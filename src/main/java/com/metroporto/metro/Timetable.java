package com.metroporto.metro;

import com.metroporto.RouteTest;
import com.metroporto.enums.TimeTableType;

import java.util.ArrayList;
import java.util.List;

public class Timetable
{
    private final int scheduleId;
    private String scheduleDescription;
    private TimeTableType timeTableType;
    private List<List<Schedule>> timeTableSchedules;

    public void setTimeTableType(TimeTableType timeTableType)
    {
        this.timeTableType = timeTableType;
    }

    public Timetable(int scheduleId, String scheduleDescription, TimeTableType timeTableType)
    {
        this.scheduleId = scheduleId;
        this.scheduleDescription = scheduleDescription;
        this.timeTableType = timeTableType;
        timeTableSchedules = new ArrayList<>();
    }

    public Timetable(TimeTableType timeTableType)
    {
        this.scheduleId = 0;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        timeTableSchedules = new ArrayList<>();
    }
    public Timetable()
    {
        this.scheduleId = 0;
        this.scheduleDescription = "";
        this.timeTableType = TimeTableType.MONDAY_TO_FRIDAY;
        timeTableSchedules = new ArrayList<>();
    }

    public void addSchedules(List<Schedule> schedules)
    {
        timeTableSchedules.add(schedules);
    }

    @Override
    public String toString()
    {
        return "Timetable{" +
                "scheduleId=" + scheduleId +
                ", scheduleDescription='" + scheduleDescription + '\'' +
                ", timeTableType=" + timeTableType +
                '}';
    }

    public void displayTimeTable()
    {
        System.out.println(toString());
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
