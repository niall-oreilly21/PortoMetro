package com.metroporto;

import com.metroporto.enums.TimeTableType;
import com.metroporto.metro.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class MetroSystem
{
    private final Map<Station, Map<Station, Double>> graph; // Graph representation of the metro system
    private final List<Line> lines; // Set to keep track of lines in the metro system
    private boolean isConnecting;
    private int index;
    private Schedule connectionSchedule;
    private LocalTime currentTime;
    Comparator<Schedule> schedulesClosestTime;

    public MetroSystem(List<Line> lines)
    {
        this.lines = lines;
        this.graph = new HashMap<>();
        this.isConnecting = false;
        this.index = 0;
        this.connectionSchedule = null;
        this.currentTime = null;
        setUpConnections();
        schedulesClosestTime = null;
    }

    private void setUpConnections()
    {
        List<Station> stations;
        for (Line line : this.lines)
        {
            stations = line.getStations();

            for (int i = 0; i < stations.size() - 1; i++)
            {
                Station stationOne = stations.get(i);
                Station stationTwo = stations.get(i + 1);

                addConnection(stationOne, stationTwo, 0.0);
            }
        }
    }

    private void addConnection(Station stationOne, Station stationTwo, double distance)
    {
        // Add a connection between two stations with the given distance and line
        if (!graph.containsKey(stationOne))
        {
            graph.put(stationOne, new HashMap<>());
        }
        if (!graph.containsKey(stationTwo))
        {
            graph.put(stationTwo, new HashMap<>());
        }

        graph.get(stationOne).put(stationTwo, distance);
        graph.get(stationTwo).put(stationOne, distance);
    }


    private List<Line> findCommonLines(List<Line> linesPreviousStation, List<Line> linesNextStation, Station currentStation, Station endStation)
    {

        List<Line>commonLines = new ArrayList<>();
        boolean foundCommonLine = false;

        for (Line line : linesPreviousStation)
        {
            if (linesNextStation.contains(line))
            {
                commonLines.add(line);
            }
        }

        for(Line line : commonLines)
        {
            // Check if the line has a route between startStation and endStation
            if (line.findRoute(currentStation, endStation) != null)
            {
                foundCommonLine = true;
                break;
            }
        }

        if (foundCommonLine)
        {
            commonLines.removeIf(line -> (line.findRoute(currentStation, endStation) == null));
        }

        return commonLines;
    }

    private void setLinesForStations(Station stationOne, Station stationTwo, List<Line> linesStationOne, List<Line> linesStationTwo)
    {
        for (Line line : lines)
        {
            if (line.getStations().contains(stationOne))
            {
                linesStationOne.add(line);
            }
            if (line.getStations().contains(stationTwo))
            {
                linesStationTwo.add(line);
            }
        }
    }

    private Line findFastestLine(List<Line> lines, Station stationOne, Station stationTwo, Station endStation, LocalTime startTime, TimeTableType timeTableType)
    {

        Line fastestLine = null;
        long fastestTravelTime = Integer.MAX_VALUE;

        for (Line line : lines)
        {
            Route route = line.findRoute(stationOne, stationTwo);

            if (route != null)
            {
                Timetable timetable = route.getTimetableByTimetableType(timeTableType);

                Schedule schedule = timetable.getClosestScheduleToStartTime(stationOne, endStation, schedulesClosestTime);

                Duration duration = Duration.between(startTime, schedule.getDepartureTime());
                long travelTime = duration.toMinutes(); // returns 90 minutes

                if (travelTime < fastestTravelTime)
                {
                    fastestLine = line;
                    fastestTravelTime = travelTime;
                }
            }
        }

        return fastestLine;
    }

    private Line getCurrentLine(Station currentStation, Station nextStation, Station endStation, LocalTime currentTime, TimeTableType timeTableType)
    {
        List<Line> linesPreviousStation = new ArrayList<>();
        List<Line> linesNextStation = new ArrayList<>();


        setLinesForStations(currentStation, nextStation, linesPreviousStation, linesNextStation);
        List<Line> commonLines = findCommonLines(linesPreviousStation, linesNextStation, currentStation, endStation);

        if (commonLines.size() == 1)
        {
            return commonLines.get(0);
        }
        else
        {
            return findFastestLine(commonLines, currentStation, nextStation, endStation, currentTime, timeTableType);
        }
    }

    public List<JourneyRoute> findShortestPath(Station startStation, Station endStation, LocalTime startTime, TimeTableType timeTableType)
    {
        List<Station> shortestPath = findShortestPath(startStation, endStation);

        currentTime = startTime;
        Line currentLine;
        isConnecting = false;
        Route currentRoute;
        JourneyRoute currentJourneyRoute = null;
        index = 0;

        List<JourneyRoute> journeyRoutes = new ArrayList<>();

        for (index = 0; index < shortestPath.size(); index++)
        {
            Station currentStation = shortestPath.get(index);
            Station nextStation = shortestPath.get(index + 1);
            schedulesClosestTime = new ComparatorSchedulesClosestTime(currentTime);

            currentLine = getCurrentLine(currentStation, nextStation, endStation, currentTime, timeTableType);

            if (currentLine != null)
            {
                currentRoute = currentLine.findRoute(currentStation, nextStation);

                if (currentRoute != null)
                {
                    currentJourneyRoute = new JourneyRoute(currentLine, currentRoute);
                    Timetable currentTimetable = currentRoute.getTimetableByTimetableType(timeTableType);

                    List<Schedule> schedules = adjustSchedulesForConnectingStation(currentJourneyRoute, currentStation, endStation, currentTimetable);

                    processSchedulesForRoute(currentJourneyRoute, schedules, shortestPath);
                }
                journeyRoutes.add(currentJourneyRoute);
            }

        }
        return journeyRoutes;
    }


    private void processSchedulesForRoute(JourneyRoute currentJourneyRoute, List<Schedule> schedules, List<Station> shortestPath)
    {
        int j = 0;

        for (;index < shortestPath.size() && j < schedules.size(); index++, j++)
        {
            Station nextStation = null;
            Schedule nextSchedule = null;

            if(index != shortestPath.size() - 1)
            {
                nextStation = shortestPath.get(index + 1);
                nextSchedule = schedules.get(j + 1);
            }

            Schedule schedule = schedules.get(j);

            if(!isConnecting)
            {
                if(index != shortestPath.size() - 1)
                {
                    if (!nextSchedule.getStation().equals(nextStation))
                    {
                        setConnectionSchedule(currentJourneyRoute, schedule);
                        break;
                    }
                    else
                    {
                        currentJourneyRoute.addSchedule(schedule);
                    }
                }
                else
                {
                    currentJourneyRoute.addSchedule(schedule);
                }

            }
            else
            {
                isConnecting = false;
            }

        }

    }

    private void setConnectionSchedule(JourneyRoute currentJourneyRoute, Schedule schedule)
    {
        isConnecting = true;
        currentTime = schedule.getDepartureTime();
        connectionSchedule = new ConnectionSchedule(schedule.getStation(), schedule.getDepartureTime());
        currentJourneyRoute.addSchedule(connectionSchedule);
        index--;
    }

    private List<Schedule> adjustSchedulesForConnectingStation(JourneyRoute currentJourneyRoute, Station currentStation, Station endStation, Timetable currentTimetable)
    {
        List<Schedule> schedules = currentTimetable.getSchedulesByStartStationAndTime(currentStation, endStation, schedulesClosestTime);

        if(isConnecting)
        {
            Schedule firstSchedule = schedules.get(0);

            // If the first schedule is within 5 minutes from the current time, use its list of schedules
            if (Duration.between(currentTime, firstSchedule.getDepartureTime()).toMinutes() <= 3)
            {
                schedulesClosestTime = new ComparatorSchedulesClosestTime(currentTime.plusMinutes(3));

                schedules = currentTimetable.getSchedulesByStartStationAndTime(currentStation, endStation, schedulesClosestTime);
            }

            firstSchedule = schedules.get(0);
            connectionSchedule.setDepartureTime(firstSchedule.getDepartureTime());
            currentJourneyRoute.addSchedule(connectionSchedule);
        }

        return schedules;
    }


    private List<Station> findShortestPath(Station startStation, Station endStation)
    {
        // Initialize distances and visited set
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, Station> previous = new HashMap<>();
        Set<Station> visited = new HashSet<>();

        for (Station station : graph.keySet())
        {
            distances.put(station, Double.MAX_VALUE);
        }

        distances.put(startStation, 0.0);

        // Create a priority queue to keep track of stations and their distances
        PriorityQueue<Station> queue = new PriorityQueue<>((station1, station2) ->
                Double.compare(distances.get(station1), distances.get(station2)));
        queue.offer(startStation);

        // Loop until destination is visited or queue is empty
        while (!queue.isEmpty())
        {
            Station currentStation = queue.poll();

            if (visited.contains(currentStation))
            {
                continue;
            }

            if (currentStation.equals(endStation))
            {
                // Destination reached, construct the shortest path
                List<Station> shortestPath = new ArrayList<>();
                while (previous.containsKey(currentStation))
                {
                    shortestPath.add(0, currentStation);
                    currentStation = previous.get(currentStation);
                }
                shortestPath.add(0, startStation);
                return shortestPath;
            }

            visited.add(currentStation);


            // Update distances to neighboring stations
            for (Station neighbor : graph.get(currentStation).keySet())
            {
                if (!visited.contains(neighbor))
                {
                    double distance = distances.get(currentStation) + graph.get(currentStation).get(neighbor);
                    if (distance < distances.get(neighbor))
                    {
                        distances.put(neighbor, distance);
                        previous.put(neighbor, currentStation);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        // No path found to destination
        return null;
    }


}