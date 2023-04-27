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


    private List<Line> findCommonLines(List<Line> linesPreviousStation, List<Line> linesNextStation, Station startStation, Station endStation)
    {

        List<Line>commonLines = new ArrayList<>();

        for (Line line : linesPreviousStation)
        {
            if (linesNextStation.contains(line))
            {
                commonLines.add(line);
            }
        }

        boolean foundLineMeetingCondition = false;

        for(Line line : commonLines)
        {
            // Check if the line has a route between startStation and endStation
            if (line.findRoute(startStation, endStation) != null)
            {
                foundLineMeetingCondition = true;
                break;
            }
        }

        // If a line meeting the condition is found, remove all other lines
        if (foundLineMeetingCondition)
        {
            commonLines.removeIf(line -> (line.findRoute(startStation, endStation) == null));
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

    private Line findFastestLine(List<Line> lines, Station stationOne, Station stationTwo, LocalTime startTime, TimeTableType timeTableType)
    {

        Line fastestLine = null;
        long fastestTravelTime = Integer.MAX_VALUE;
        List<Schedule> schedules = new ArrayList<>();

        for (Line line : lines)
        {
            Route route = line.findRoute(stationOne, stationTwo);

            if (route != null)
            {
                Timetable timetable = route.getTimetableByTimetableType(timeTableType);
                Schedule schedule = timetable.getClosestScheduleToStartTime(stationOne, startTime);

                schedules.add(schedule);

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

    public void findShortestPath(Station startStation, Station endStation, LocalTime startTime, TimeTableType timeTableType)
    {
        List<Station> shortestPath = findShortestPath(startStation, endStation);
        // Determine schedules for each station in the shortest path
        LocalTime currentTime = startTime;
        Line currentLine = null;
        boolean isChangingLine;
        boolean isConnecting = false;
        Route currentRoute = null;
        List<Schedule> schedulesShortestPath = new ArrayList<>();

        for (int i = 0; i < shortestPath.size(); i++)
        {
            Station current = shortestPath.get(i);
            Station next = shortestPath.get(i + 1);
            Station previous = (i > 0) ? shortestPath.get(i - 1) : null;

            List<Line> linesPreviousStation = new ArrayList<>();
            List<Line> linesNextStation = new ArrayList<>();

            setLinesForStations(current, next, linesPreviousStation, linesNextStation);
            List<Line> commonLines = findCommonLines(linesPreviousStation, linesNextStation, current, endStation);

            if (commonLines.size() == 1)
            {
                currentLine = commonLines.get(0);
            }
            else
            {
                currentLine = findFastestLine(commonLines, current, next, currentTime, timeTableType);
            }

            if (currentLine != null)
            {
                System.out.println("----Line: " + currentLine.getLineName() + "-----");

                currentRoute = currentLine.findRoute(current, next);

                if (currentRoute != null)
                {
                    Timetable currentTimetable = currentRoute.getTimetableByTimetableType(timeTableType);



                    List<Schedule> schedules = currentTimetable.getSchedulesByStartStationAndTime(current, currentTime);

                    if(isConnecting)
                    {
                        Schedule firstSchedule = schedules.get(0);

                        // If the first schedule is within 5 minutes from the current time, use its list of schedules
                        if (Duration.between(currentTime, firstSchedule.getDepartureTime()).toMinutes() <= 3)
                        {
                            schedules = currentTimetable.getSchedulesByStartStationAndTime(current, currentTime.plusMinutes(3));
                        }
                        isConnecting = false;
                    }


                    System.out.println("----Route: Final Destination " + currentRoute.getEndStation().getStationName() + "-----");

                    isChangingLine = false;

                    int j = 0;

                    while (i < shortestPath.size() - 1  && !isChangingLine)
                    {
                        current = shortestPath.get(i);
                        next = shortestPath.get(i + 1);
                        previous = (i > 0) ? shortestPath.get(i - 1) : null;

                        Schedule schedule = schedules.get(j);
                        Schedule nextSchedule = schedules.get(j + 1);

                        schedulesShortestPath.add(schedule);

                        if (!nextSchedule.getStation().equals(next) || nextSchedule.getDepartureTime().equals(LocalTime.of(4,00)))
                        {

                            System.out.println("Connection: " + current.getStationName());
                            isChangingLine = true;
                            isConnecting = true;
                            currentTime = schedule.getDepartureTime();
                            i--;
                        }
                        else
                        {
                            System.out.println("Station: " + schedule.getStation().getStationName());
                            System.out.println("Departure time: " + schedule.getDepartureTime());
                            System.out.println();
                        }

                        if(!isChangingLine)
                        {
                            i++;
                            j++;
                        }
                    }

                }
            }

        }


//        System.out.println("LIST");
//        for(Schedule schedule : schedulesShortestPath)
//        {
//            System.out.println("Station: " + schedule.getStation().getStationName());
//            System.out.println("Departure time: " + schedule.getDepartureTime());
//            System.out.println();
//        }
    }

//        public void findShortestPath(Station startStation, Station endStation, LocalTime startTime, TimeTableType timeTableType) {
//        List<Station> shortestPath = findShortestPath(startStation, endStation);
//
//        // Determine schedules for each station in the shortest path
//        LocalTime currentTime = startTime;
//        Line currentLine = null;
//        boolean changingLine;
//        Route currentRoute = null;
//        List<Schedule> schedulesShortestPath = new ArrayList<>();
//
//        Iterator<Station> iterator = shortestPath.iterator();
//        Station current = iterator.next();
//        Station next;
//
//        while (iterator.hasNext()) {
//            next = iterator.next();
//            Station previous = (current == startStation) ? null : shortestPath.get(shortestPath.indexOf(current) - 1);
//
//            List<Line> linesPreviousStation = new ArrayList<>();
//            List<Line> linesNextStation = new ArrayList<>();
//
//            setLinesForStations(previous, next, linesPreviousStation, linesNextStation);
//            List<Line> commonLines = findCommonLines(linesPreviousStation, linesNextStation);
//
//            if (commonLines.size() == 1) {
//                currentLine = commonLines.get(0);
//            } else {
//                currentLine = findFastestLine(commonLines, current, next, currentTime, timeTableType);
//            }
//
//            if (currentLine != null) {
//                currentRoute = currentLine.findRoute(current, next);
//
//                if (currentRoute != null) {
//                    Timetable currentTimetable = currentRoute.getTimetableByTimetableType(timeTableType);
//
//                    List<Schedule> schedules = currentTimetable.getSchedulesByStartStationAndTime(current, currentTime);
//
//                    System.out.println("Final Destination " + currentRoute.getEndStation().getStationName());
//
//                    changingLine = false;
//
//                    int j = 0;
//                    while (iterator.hasNext() && !changingLine) {
//                        Schedule schedule = schedules.get(j);
//
//                        if (schedule.getStation().equals(current)) {
//                            schedulesShortestPath.add(schedule);
//                            System.out.println("Station: " + schedule.getStation().getStationName());
//                            System.out.println("Departure time: " + schedule.getDepartureTime());
//                            System.out.println();
//                        } else {
//                            System.out.println("Connection: " + previous.getStationName());
//                            System.out.println();
//                            currentTime = schedule.getDepartureTime();
//                            changingLine = true;
//                        }
//
//                        j++;
//                    }
//                }
//            }
//
//            current = next;
//        }
//
//        // Add the last station to the schedulesShortestPath list
//        Station lastStation = shortestPath.get(shortestPath.size() - 1);
//        List<Schedule> lastSchedules = currentRoute.getTimetableByTimetableType(timeTableType)
//                .getSchedulesByStartStationAndTime(lastStation, currentTime);
//        schedulesShortestPath.addAll(lastSchedules);
//    }





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
