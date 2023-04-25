package com.metroporto.metro;

import java.util.Objects;

public class Facility implements Comparable<Facility>
{
    private final int facilityId;
    private String facilityDescription;

    public Facility(int facilityID, String facilityDescription)
    {
        this.facilityId = facilityID;
        this.facilityDescription = facilityDescription;
    }

    public int getFacilityId()
    {
        return facilityId;
    }

    public String getFacilitiesDescription()
    {
        return facilityDescription;
    }

    @Override
    public String toString()
    {
        return facilityDescription;
    }

    @Override
    public int compareTo(Facility otherFacility)
    {
        return this.facilityId - otherFacility.getFacilityId();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return facilityId == facility.facilityId && Objects.equals(facilityDescription, facility.facilityDescription);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(facilityId, facilityDescription);
    }
}
