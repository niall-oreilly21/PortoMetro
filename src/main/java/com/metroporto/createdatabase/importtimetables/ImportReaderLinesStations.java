package com.metroporto.createdatabase.importtimetables;

import com.metroporto.metro.Line;
import com.metroporto.metro.Station;

import java.util.List;

public abstract class ImportReaderLinesStations implements StartInterface
{
    protected List<Station> stations;
    protected List<Line> lines;
    protected Line line;
    protected boolean useRouteOne;

    public ImportReaderLinesStations(List<Station> stations, List<Line> lines)
    {
        this.stations = stations;
        this.lines = lines;
        line = null;
    }

    public void changeLine(String lineId)
    {
        for(Line line : lines)
        {
            if(line.getLineId().equalsIgnoreCase(lineId))
            {
                this.line = line;
            }
        }
    }

    public void switchRoute()
    {
        useRouteOne = !useRouteOne;
    }

}
