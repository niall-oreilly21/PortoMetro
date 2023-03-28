package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.cards.StudentCard;

public class Passenger extends User
{
    private String name;
    private String email;
    private String phoneNumber;
    private Card metroCard;

    public Passenger(int userID, String username, String password, String name, String email, String phoneNumber, Card metroCard)
    {
        super(userID, username, password);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        checkMetroCard(metroCard);
    }

    private void checkMetroCard(Card metroCard)
    {
        if(this.metroCard instanceof StudentCard)
        {
            if(Passenger.class.isInstance(Student.class))
            {
                    this.metroCard = metroCard;
            }
            else
            {
                System.out.println("You can't have a student card unless you are a student.");
                this.metroCard = null;
            }
        }
        else
        {
            this.metroCard = metroCard;
        }
    }
    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public Card getMetroCard()
    {
        return metroCard;
    }
}
