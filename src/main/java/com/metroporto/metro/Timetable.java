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
    private Map <Station, List<Schedule>> schedulesByStation;
    private Map <Station, Integer> stationsRowIndex;

    public Timetable(int timetableId, TimeTableType timeTableType, List<List<Schedule>> timetableSchedules)
    {
        this.timetableId = timetableId;
        this.scheduleDescription = "";
        this.timeTableType = timeTableType;
        this.timetableSchedules = timetableSchedules;
        this.schedulesByStation = new LinkedHashMap<>();
        this.stationsRowIndex = new HashMap<>();
        setScheduleStationOrder();
        setSchedulesByStation();
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

    public List<Station> getStationsForTimetable()
    {
        return new ArrayList<>(schedulesByStation.keySet());
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

    public void setScheduleStationOrder()
    {
        List<Schedule> uniqueScheduleList = null;

        for (List<Schedule> schedules : this.timetableSchedules)
        {
            Set<LocalTime> uniqueTimes = new HashSet<>();

            boolean isUniqueList = true;

            for (Schedule schedule : schedules)
            {
                if (uniqueTimes.contains(schedule.getDepartureTime()))
                {
                    isUniqueList = false;
                    break;
                }
                else
                {
                    uniqueTimes.add(schedule.getDepartureTime());
                }
            }
            if (isUniqueList)
            {
                uniqueScheduleList = schedules;
                break;
            }
        }

        if (uniqueScheduleList != null)
        {
            Collections.sort(uniqueScheduleList);

            Map<Station, List<Schedule>> sortedMap = new LinkedHashMap<>();

            int index = 0;

            for (Schedule schedule : uniqueScheduleList)
            {
                schedulesByStation.put(schedule.getStation(), new ArrayList<>());
                stationsRowIndex.put(schedule.getStation(), index);
                index++;
            }
        }
        else
        {
            System.out.println("No unique schedule list found.");
        }
    }

    public void displayStationForScheduleInOrder()
    {
        for (Station key : schedulesByStation.keySet())
        {
            System.out.println(key.getStationName());
        }
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

        int columnIndex = schedulesByStation.indexOf(schedule);
        int rowIndex = stationsRowIndex.get(station);

        int size = timetableSchedules.get(columnIndex).size();

        if (columnIndex < size)
        {
            return timetableSchedules.get(rowIndex).subList(columnIndex, size);
        }

        return null;
    }

    public List<Schedule> getSchedulesByStation(Station station)
    {
        if(this.schedulesByStation.containsKey(station))
        {
            return this.schedulesByStation.get(station);
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
        Duration minTimeDifference =  Duration.ofSeconds(Long.MAX_VALUE);
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

    public void setSchedulesByStation()
    {
        for (List<Schedule> scheduleList : timetableSchedules)
        {
            for (Schedule schedule : scheduleList)
            {
                Station station = schedule.getStation();
                if (!schedulesByStation.containsKey(station))
                {
                    schedulesByStation.put(station, new ArrayList<>());
                }

                if(!schedule.getDepartureTime().equals(LocalTime.of(4, 00)))
                {
                    schedulesByStation.get(station).add(schedule);
                }

            }
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return timetableId == timetable.timetableId && Objects.equals(scheduleDescription, timetable.scheduleDescription) && timeTableType == timetable.timeTableType && Objects.equals(timetableSchedules, timetable.timetableSchedules) && Objects.equals(schedulesByStation, timetable.schedulesByStation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(timetableId, scheduleDescription, timeTableType, timetableSchedules, schedulesByStation);
    }
}
