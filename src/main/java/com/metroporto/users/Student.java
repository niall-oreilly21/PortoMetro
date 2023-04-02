package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.metro.University;

public class Student extends Passenger
{
    private University university;

    public Student(int userID, String username, String password, Card metroCard, University university)
    {
        super(userID, username, password, metroCard);
        this.university = university;
    }

    public University getUniversity()
    {
        return university;
    }
}
