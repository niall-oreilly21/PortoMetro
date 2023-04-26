package com.metroporto;

import com.metroporto.enums.TimeTableType;
import com.metroporto.metro.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class MetroSystem
{
    private Map<Station, Map<Station, Double>> graph; // Graph representation of the metro system
    private List<Line> lines; // Set to keep track of lines in the metro system

    public MetroSystem(List<Line> lines)
    {
        this.lines = lines;
        this.graph = new HashMap<>();

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

    public void addConnection(Station stationOne, Station stationTwo, double distance)
    {
        // Add a connection between two stations with the given distance and line
        if (!graph.containsKey(stationOne)) {
            graph.put(stationOne, new HashMap<>());
        }
        if (!graph.containsKey(stationTwo)) {
            graph.put(stationTwo, new HashMap<>());
        }

        graph.get(stationOne).put(stationTwo, distance);
        graph.get(stationTwo).put(stationOne, distance);
    }


    public void findShortestPath(Station startStation, Station endStation, LocalTime startTime, TimeTableType timeTableType)
    {
        List<Station> shortestPath = findShortestPath(startStation,endStation);
        // Determine schedules for each station in the shortest path
        LocalTime currentTime = startTime;
        Line currentLine = null;
        boolean changingLine = false;
        Route currentRoute = null;
        //Line commonLine = null; // Initialize to null

        for (int i = 0; i < shortestPath.size() - 1; i++)
        {
            Station current = shortestPath.get(i);
            Station next = shortestPath.get(i + 1);
            Station previous = (i > 0) ? shortestPath.get(i - 1) : null;

            List<Line> linesPreviousStation = new ArrayList<>();
            List<Line> linesNextStation = new ArrayList<>();

            for (Line line : lines)
            {
                if (line.getStations().contains(previous))
                {
                    linesPreviousStation.add(line);
                }
                if (line.getStations().contains(next))
                {
                    linesNextStation.add(line);
                }
            }

            Line commonLine = null; // Initialize to null

            for (Line line : linesPreviousStation)
            {
                if (linesNextStation.contains(line))
                {
                    if (commonLine == null)
                    {
                        commonLine = line;
                    }
                    else if (!commonLine.equals(line))
                    {
                        commonLine = line; // Update commonLine if not equal to current line
                        break; // Break out of the loop after finding the common line
                    }
                }
            }

            if (commonLine != null)
            {
                // Check if changing line
                if (previous != null && !commonLine.equals(currentLine))
                {
                    changingLine = true;
                }
                else
                {
                    changingLine = false;
                }

                if (changingLine)
                {
                    currentLine = commonLine;
                }

                // Print station name and line name
                System.out.println("Station: " + current.getStationName() + ", Line: " + currentLine.getLineName());
            }
            else
            {
                System.out.println("No common line found for station: " + current.getStationName());
            }

            if (currentLine != null)
            {
                currentRoute = currentLine.findRoute(current, next);

                if(currentRoute != null)
                {
                    Timetable currentTimetable = currentRoute.getTimetableByTimetableType(timeTableType);

                    List<Schedule> schedulesStation = currentTimetable.getSchedulesByStation(startStation);

                    Duration minTimeDifference =  Duration.ofSeconds(Long.MAX_VALUE); // Step 2
                    Schedule closestSchedule = null;

                    for (Schedule schedule : schedulesStation)
                    { // Step 3
                        LocalTime scheduleTime = schedule.getDepartureTime(); // Assuming Schedule class has a getLocalTime() method to get the local time field
                        Duration timeDifference = Duration.between(currentTime, scheduleTime);

                        if (timeDifference.abs().compareTo(minTimeDifference.abs()) < 0)
                        { // Step 4
                            minTimeDifference = timeDifference.abs();
                            closestSchedule = schedule;
                        }
                    }

                    System.out.println(closestSchedule.getStation().getStationName());
                    System.out.println(closestSchedule);

                    System.out.println("Final Destination " + currentRoute.getEndStation().getStationName());
                    System.out.println();
                }
            }
        }
    }



    public List<Station> findShortestPath(Station startStation, Station endStation)
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

            if (visited.contains(currentStation)) {
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
