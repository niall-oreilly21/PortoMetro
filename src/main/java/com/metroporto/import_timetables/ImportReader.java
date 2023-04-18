package com.metroporto.import_timetables;

import com.metroporto.metro.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ImportReader
{
    List<Station>stations;

    public ImportReader(List<Station> stations)
    {
        this.stations = stations;
    }

    private List<Station> getStations(String text)
    {
        String output = text.replaceAll("\\d{1,2}:\\d{2}\\s+", "");

        List<String> onlyText = Arrays.asList(output.split("\n"));

        List<Station>stationList = new ArrayList<>();

        for (String stationText : onlyText)
        {

            for (Station station : stations)
            {
                if (stationText.toLowerCase().contains(station.getStationName().toLowerCase()))
                {
                    stationList.add(station);
                }
            }
        }

        return stationList;
    }
}
