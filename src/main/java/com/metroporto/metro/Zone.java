package com.metroporto.metro;

import java.util.Objects;

public class Zone implements Comparable<Zone>
{
    private int zoneID;
    private String zoneName;

    public Zone(int zoneID, String zoneName)
    {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
    }

    public int getZoneID()
    {
        return zoneID;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    @Override
    public String toString()
    {
        return zoneName;
    }

    @Override
    public int compareTo(Zone otherZone)
    {
        return this.zoneID - otherZone.zoneID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return zoneID == zone.zoneID && Objects.equals(zoneName, zone.zoneName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(zoneID, zoneName);
    }
}
