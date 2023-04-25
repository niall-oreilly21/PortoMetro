package com.metroporto.metro;

public class University implements Comparable<University>
{
    private final String universityId;
    private String universityName;


    public University(String universityId, String universityName)
    {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public String getUniversityId()
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
        return this.universityId.compareToIgnoreCase(otherUniversity.getUniversityId());
    }
}
