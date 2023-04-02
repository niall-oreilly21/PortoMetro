package com.metroporto.metro;

public class Facility implements Comparable<Facility>
{
    private final int FACILITY_ID;
    private String facilityDescription;

    public Facility(int facilityID, String facilityDescription)
    {
        this.FACILITY_ID = facilityID;
        this.facilityDescription = facilityDescription;
    }

    public int getFACILITY_ID()
    {
        return FACILITY_ID;
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
        return this.FACILITY_ID - otherFacility.getFACILITY_ID();
    }
}
