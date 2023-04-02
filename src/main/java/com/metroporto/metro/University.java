package com.metroporto.metro;

public class University implements Comparable<University>
{
    private final int UNIVERSITY_ID;
    private String universityName;


    public University(int universityID, String universityName)
    {
        UNIVERSITY_ID = universityID;
        this.universityName = universityName;
    }

    public int getUNIVERSITY_ID()
    {
        return UNIVERSITY_ID;
    }

    public String getUniversityName()
    {
        return universityName;
    }

    @Override
    public String toString()
    {
        return universityName;
    }

    @Override
    public int compareTo(University otherUniversity)
    {
        return this.UNIVERSITY_ID - otherUniversity.getUNIVERSITY_ID();
    }
}
