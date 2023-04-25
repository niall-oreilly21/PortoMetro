package com.metroporto.metro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station implements Comparable<Station>
{
    private final String stationId;
    private Zone zone;
    private String stationName;
    private List<Facility> facilities;

    public Station(String stationId, Zone zone, String stationName, List<Facility> facilities)
    {
        this.stationId = stationId;
        this.zone = zone;
        this.stationName = stationName;
        this.facilities = facilities;
    }

    public Station(String stationId, Zone zone, String stationName)
    {
        this.stationId = stationId;
        this.zone = zone;
        this.stationName = stationName;
        this.facilities = new ArrayList<>();
    }

    public String getStationId()
    {
        return stationId;
    }

    public Zone getZone()
    {
        return zone;
    }

    public String getStationName()
    {
        return stationName;
    }

    public List<Facility> getFacilities()
    {
        return facilities;
    }

    public void addFacility(Facility facility)
    {
        facilities.add(facility);
    }

    @Override
    public int compareTo(Station otherStation)
    {
        if(this.zone.equals(otherStation.zone))
        {
            return this.stationId.compareToIgnoreCase(otherStation.stationId);
        }

        return this.zone.compareTo(otherStation.zone);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationId, station.stationId) && Objects.equals(zone, station.zone) && Objects.equals(stationName, station.stationName) && Objects.equals(facilities, station.facilities);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(stationId, zone, stationName, facilities);
    }

    @Override
    public String toString()
    {
        return "Station{" +
                "stationId='" + stationId + '\'' +
                ", zone=" + zone +
                ", stationName='" + stationName + '\'' +
                ", facilities=" + facilities +
                '}';
    }
}
