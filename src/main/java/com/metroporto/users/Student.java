package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.metro.University;

public class Student extends Passenger
{
    private University university;

    public Student(int userId, String email, String password, String firstName, String lastName, Card metroCard, University university)
    {
        super(userId, email, password, firstName, lastName, metroCard);
        this.university = university;
    }

    public Student(String email, String password, String firstName, String lastName, Card metroCard, University university)
    {
        super(email, password, firstName, lastName, metroCard);
        this.university = university;
    }

    public University getUniversity()
    {
        return university;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "university=" + university +
                "} " + super.toString();
    }
}
