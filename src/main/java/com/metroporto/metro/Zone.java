package com.metroporto.metro;

import java.util.Objects;

public class Zone implements Comparable<Zone>
{
    private final int zoneId;
    private String zoneName;

    public Zone(int zoneID, String zoneName)
    {
        this.zoneId = zoneID;
        this.zoneName = zoneName;
    }

    public int getZoneId()
    {
        return zoneId;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    @Override
    public String toString()
    {
        return "Zone{" +
                "zoneId=" + zoneId +
                ", zoneName='" + zoneName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Zone otherZone)
    {
        return this.zoneId - otherZone.zoneId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return zoneId == zone.zoneId && Objects.equals(zoneName, zone.zoneName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(zoneId, zoneName);
    }
}
