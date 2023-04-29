package com.metroporto.metro;

import com.metroporto.ComparatorSchedules;
import com.metroporto.enums.TimeTableType;

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
        setTimetableRowOrder();
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

    private void setScheduleStationOrder()
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

            int index = 0;

            for (Schedule schedule : uniqueScheduleList)
            {
                if(!schedulesByStation.containsKey(schedule.getStation()))
                {
                    schedulesByStation.put(schedule.getStation(), new ArrayList<>());
                    stationsRowIndex.put(schedule.getStation(), index);
                    index++;
                }
            }
        }
        else
        {
            System.out.println("No unique schedule list found.");
        }
    }

    private void setTimetableRowOrder()
    {
        Comparator<Schedule> timetableRowComparator = new ComparatorSchedules(new LinkedHashSet<>(schedulesByStation.keySet()));

        for(List<Schedule> schedules : this.timetableSchedules)
        {
            schedules.sort(timetableRowComparator);
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

    public List<Schedule> getSchedulesByStation(Station station)
    {
        if(this.schedulesByStation.containsKey(station))
        {
            return this.schedulesByStation.get(station);
        }

        return null;
    }

    public List<Schedule> getSchedulesByStartStationAndTime(Station station, Station endStation, Comparator<Schedule> schedulesClosestTime)
    {
        List<Schedule>schedulesByStationList = getSchedulesByStation(station);

        Schedule schedule = getClosestScheduleToStartTime(schedulesByStationList, endStation, schedulesClosestTime);

        return  getRowOfSchedules(schedulesByStationList, schedule);
    }

    public Schedule getClosestScheduleToStartTime(Station station, Station endStation, Comparator<Schedule> schedulesClosestTime)
    {
        List<Schedule>schedulesByStation = getSchedulesByStation(station);

        if(schedulesByStation != null)
        {
            return getClosestScheduleToStartTime(schedulesByStation, endStation, schedulesClosestTime);
        }
        return null;
    }

    private Schedule getClosestScheduleToStartTime(List<Schedule> schedules, Station endStation, Comparator<Schedule> schedulesClosestTime)
    {
        PriorityQueue<Schedule> scheduleQueue = new PriorityQueue<>(schedulesClosestTime);

        for (Schedule schedule : schedules)
        {
            List<Schedule> scheduleList = getRowOfSchedules(schedules, schedule);

            if (!checkEndScheduleInvalid(scheduleList, endStation))
            {
                scheduleQueue.offer(schedule);
            }
        }

        return scheduleQueue.peek();
    }

    private boolean checkEndScheduleInvalid(List<Schedule>schedules, Station endStation)
    {
        for (Schedule schedule : schedules)
        {
            if(schedule.getStation().equals(endStation) && schedule.getDepartureTime().equals(LocalTime.of(4, 00)))
            {
                return true;
            }
        }
        return false;
    }

    private List<Schedule> getRowOfSchedules(List<Schedule>schedules, Schedule schedule)
    {
        int columnIndex = schedules.indexOf(schedule);
        int rowIndex = stationsRowIndex.get(schedule.getStation());

        int size = stationsRowIndex.size();

        if (rowIndex < size)
        {
            return timetableSchedules.get(columnIndex).subList(rowIndex, size);
        }
        return null;
    }


    public void setSchedulesByStation()
    {
        for (List<Schedule> scheduleList : timetableSchedules)
        {
            for (Schedule schedule : scheduleList)
            {
                Station station = schedule.getStation();

                if (schedulesByStation.containsKey(station))
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
