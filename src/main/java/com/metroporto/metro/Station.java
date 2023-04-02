package com.metroporto.metro;

public class Station implements Comparable<Station>
{
    private final String STATION_ID;
    private Zone zone;
    private String stationName;

    public Station(String STATION_ID, Zone zone, String stationName)
    {
        this.STATION_ID = STATION_ID;
        this.zone = zone;
        this.stationName = stationName;
    }

    public String getSTATION_ID()
    {
        return STATION_ID;
    }

    public Zone getZone()
    {
        return zone;
    }

    public String getStationName()
    {
        return stationName;
    }

    @Override
    public int compareTo(Station otherStation)
    {
        if(this.zone.equals(otherStation.zone))
        {
            return this.STATION_ID.compareToIgnoreCase(otherStation.STATION_ID);
        }

        return this.zone.compareTo(otherStation.zone);
    }

    @Override
    public String toString()
    {
        return "Station{" +
                "STATION_ID='" + STATION_ID + '\'' +
                ", zone=" + zone +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
