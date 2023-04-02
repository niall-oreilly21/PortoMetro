package com.metroporto.metro;

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
}
