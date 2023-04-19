package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.metro.University;

public class Student extends Passenger
{
    private University university;

    public Student(int userId, String email, String password, Card metroCard, University university)
    {
        super(userId, email, password, metroCard);
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
