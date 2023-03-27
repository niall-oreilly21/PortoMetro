package com.metroporto.users;

import com.metroporto.cards.Card;

public class Student extends Passenger
{
    private String university;

    public Student(int userID, String username, String password, String name, String email, String phoneNumber, Card metroCard)
    {
        super(userID, username, password, name, email, phoneNumber, metroCard);
    }

    public String getUniversity()
    {
        return university;
    }
}
