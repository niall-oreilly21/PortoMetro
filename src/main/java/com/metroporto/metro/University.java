package com.metroporto.metro;

public class University implements Comparable<University>
{
    private final int universityId;
    private String universityName;


    public University(int universityID, String universityName)
    {
        universityId = universityID;
        this.universityName = universityName;
    }

    public int getUniversityId()
    {
        return universityId;
    }

    public String getUniversityName()
    {
        return universityName;
    }

    @Override
    public String toString()
    {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                '}';
    }

    @Override
    public int compareTo(University otherUniversity)
    {
        return this.universityId - otherUniversity.getUniversityId();
    }
}
