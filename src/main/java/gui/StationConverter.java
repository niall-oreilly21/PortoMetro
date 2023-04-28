package gui;

import com.metroporto.metro.Station;
import javafx.util.StringConverter;

public class StationConverter extends StringConverter<Station>
{
    @Override
    public String toString(Station station)
    {
        return station.getStationName();
    }

    @Override
    public Station fromString(String s)
    {
        return null;
    }
}
