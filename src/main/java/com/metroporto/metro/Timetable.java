package com.metroporto.metro;

import com.metroporto.ComparatorSchedules;
import com.metroporto.enums.TimeTableType;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Timetable
{
    private int timetableId;

    private String scheduleDescription;
    private TimeTableType timeTableType;
    private List<List<Schedule>> timetableSchedules;
    private Set<Station> stationSet;
    private Set<Station>stations;

    public Set<Station> getStations()
    {
        return stations;
    }

    public Timetable(int timetableId, TimeTableType timeTableType, List<List<Schedule>> timetableSchedules)
    {
        this.timetableId = timetableId;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        this.timetableSchedules = timetableSchedules;

        stationSet = new LinkedHashSet<>();
        stations = new LinkedHashSet<>();

        List<Schedule> uniqueScheduleList = null; // Initialize with null to indicate no unique schedule list found

        for (List<Schedule> schedules : this.timetableSchedules)
        {
            Set<LocalTime> uniqueTimes = new HashSet<>();

            boolean isUniqueList = true;

            for (Schedule schedule : schedules)
            {
                if (uniqueTimes.contains(schedule.getDepartureTime()))
                {
                    isUniqueList = false;
                    break; // Break out of inner loop if duplicate time found
                }
                else
                {
                    uniqueTimes.add(schedule.getDepartureTime());
                }
            }
            if (isUniqueList)
            {
                uniqueScheduleList = schedules;
                break; // Break out of outer loop if unique list found
            }
        }

        if (uniqueScheduleList != null)
        {
            Collections.sort(uniqueScheduleList);
            for (Schedule schedule : uniqueScheduleList)
            {
                stations.add(schedule.getStation());
            }
        } else
        {
            System.out.println("No unique schedule list found.");
        }


        Collections.sort(this.timetableSchedules.get(9));

        for(Schedule schedules : this.timetableSchedules.get(9))
        {
            stationSet.add(schedules.getStation());
            stations.add(schedules.getStation());
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

    public List<Schedule> getSchedulesByStartStationAndTime(Station station, LocalTime startTime)
    {
        List<Schedule>schedulesByStation = getSchedulesByStation(station);

        Schedule schedule = getClosestScheduleToStartTime(schedulesByStation, startTime);

        int rowIndex = schedulesByStation.indexOf(schedule);
        int columnIndex = getIndex(station);

        int size = timetableSchedules.get(columnIndex).size();

        if (columnIndex < size)
        {
            return timetableSchedules.get(rowIndex).subList(columnIndex, size);
        }

        return null;

    }

    public Schedule getClosestScheduleToStartTime(Station station, LocalTime startTime)
    {
        List<Schedule>schedulesByStation = getSchedulesByStation(station);

        if(schedulesByStation != null)
        {
            return getClosestScheduleToStartTime(schedulesByStation, startTime);
        }
        return null;
    }

    private Schedule getClosestScheduleToStartTime(List<Schedule> schedules, LocalTime startTime)
    {
        Duration minTimeDifference =  Duration.ofSeconds(Long.MAX_VALUE); // Step 2
        Schedule closestSchedule = null;

        for (Schedule schedule : schedules)
        {
            LocalTime scheduleTime = schedule.getDepartureTime();

            if (scheduleTime.equals(startTime))
            {
                minTimeDifference = Duration.ZERO;
                closestSchedule = schedule;
            }
            else if (scheduleTime.isAfter(startTime))
            {
                Duration timeDifference = Duration.between(startTime, scheduleTime);

                if (timeDifference.abs().compareTo(minTimeDifference.abs()) < 0)
                {
                    minTimeDifference = timeDifference.abs();
                    closestSchedule = schedule;
                }
            }
        }

        return closestSchedule;
    }

    private List<Schedule> getSchedulesByStation(Station station)
    {
        int index = getIndex(station);

        if(index != -1)
        {
            List<Schedule>scheduleList = new ArrayList<>();

            for (List<Schedule> schedules : timetableSchedules)
            {
                scheduleList.add(schedules.get(index));
            }

            return scheduleList;
        }

        return null;
    }

    private int getIndex(Station station)
    {
        int index = -1;

        for (int i = 0; i < timetableSchedules.get(0).size(); i++)
        {
                if(timetableSchedules.get(0).get(i).getStation().equals(station))
                {
                    index = i;
                    break;
                }

        }

        return index;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return timetableId == timetable.timetableId && Objects.equals(scheduleDescription, timetable.scheduleDescription) && timeTableType == timetable.timeTableType && Objects.equals(timetableSchedules, timetable.timetableSchedules) && Objects.equals(stationSet, timetable.stationSet) && Objects.equals(stations, timetable.stations);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(timetableId, scheduleDescription, timeTableType, timetableSchedules, stationSet, stations);
    }
}
