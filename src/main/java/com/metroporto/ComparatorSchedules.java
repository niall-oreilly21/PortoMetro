package com.metroporto;

import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ComparatorSchedules implements Comparator<Schedule>
{
    private Set<Station> stations;

    public ComparatorSchedules(Set<Station> stationList)
    {

        this.stations = stationList;
    }

    @Override
    public int compare(Schedule scheduleOne, Schedule scheduleTwo)
    {
        Station stationOne = scheduleOne.getStation();
        Station stationTwo = scheduleTwo.getStation();

        // Get the index of the stations in the stationList set
        int indexOne = getIndex(stationOne);
        int indexTwo = getIndex(stationTwo);

        // Compare based on the index
        return Integer.compare(indexOne, indexTwo);
    }

    // Helper method to get the index of a station in the stationList set
    private int getIndex(Station station)
    {
        Iterator<Station> iterator = stations.iterator();
        int index = 0;

        while (iterator.hasNext())
        {
            if (iterator.next().equals(station))
            {
                return index;
            }
            index++;
        }
        // Station not found in the set, return a large value
        return Integer.MAX_VALUE;
    }
}
