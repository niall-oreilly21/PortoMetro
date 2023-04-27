package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.cards.StudentCard;

public class Passenger extends User
{
    private Card metroCard;

    public Passenger(int userId, String email, String password, String firstName, String lastName, Card metroCard)
    {
        super(userId, email, password, firstName, lastName);
        checkMetroCard(metroCard);
    }

    public Passenger(int userId, String email, String password, String firstName, String lastName)
    {
        super(userId, email, password, firstName, lastName);
        this.metroCard = null;;
    }

    public Passenger(String email, String password, String firstName, String lastName, Card metroCard)
    {
        super(email, password, firstName,  lastName);
        checkMetroCard(metroCard);
    }

    public Passenger(String email, String password, String firstName, String lastName)
    {
        super(email, password, firstName,  lastName);
        this.metroCard = null;
    }

    private void checkMetroCard(Card metroCard)
    {
        if(this.metroCard instanceof StudentCard)
        {
            System.out.println("You can't have a student card unless you are a student.");
            this.metroCard = null;
        }
        else
        {
            this.metroCard = metroCard;
        }
    }

    public Card getMetroCard()
    {
        return metroCard;
    }

    public void setMetroCard(Card metroCard)
    {
        checkMetroCard(metroCard);
    }

    @Override
    public String toString()
    {
        return "Passenger{" +
                "metroCard=" + metroCard +
                "} " + super.toString();
    }
}
