package com.metroporto.metro;

import com.metroporto.ComparatorSchedules;
import com.metroporto.enums.TimeTableType;

import java.util.*;

public class Timetable
{
    private int timetableId;

    private String scheduleDescription;
    private TimeTableType timeTableType;
    private List<List<Schedule>> timetableSchedules;
    private Set<Station> stationSet;

    public Timetable(int timetableId, TimeTableType timeTableType, List<List<Schedule>> timetableSchedules)
    {
        this.timetableId = timetableId;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        this.timetableSchedules = timetableSchedules;

        stationSet = new LinkedHashSet<>();

        Collections.sort(this.timetableSchedules.get(0));

        for(Schedule schedules : this.timetableSchedules.get(0))
        {
            stationSet.add(schedules.getStation());
        }

        for(List<Schedule> schedules : this.timetableSchedules)
        {
            schedules.sort(new ComparatorSchedules(stationSet));
        }

    }

    public Timetable(TimeTableType timeTableType)
    {
        this.timetableId = 0;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        timetableSchedules = new ArrayList<>();
    }

    public Timetable(int timetableId, TimeTableType timeTableType)
    {
        this.timetableId = timetableId;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        timetableSchedules = new ArrayList<>();
    }

    public Timetable()
    {
        this.timetableId = 0;
        this.scheduleDescription = "";
        this.timeTableType = TimeTableType.MONDAY_TO_FRIDAY;
        timetableSchedules = new ArrayList<>();
    }

    public void addSchedules(List<Schedule> schedules)
    {
        timetableSchedules.add(schedules);
    }

    public int getTimetableId()
    {
        return timetableId;
    }

    public List<List<Schedule>> getTimetableSchedules()
    {
        return timetableSchedules;
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

    public void setTimetableId(int timetableId)
    {
        this.timetableId = timetableId;
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
        System.out.println("-----" + timeTableType.getLabel()+ "-----");
        for(List<Schedule> schedules : timetableSchedules)
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

        for (List<Schedule> schedules : timetableSchedules)
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
        List<Schedule> row = timetableSchedules.get(0);

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
