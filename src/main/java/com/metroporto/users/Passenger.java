package com.metroporto.users;

import com.metroporto.cards.Card;
import com.metroporto.cards.StudentCard;

public class Passenger extends User
{
    protected Card metroCard;

    public Passenger(int userId, String email, String password, Card metroCard)
    {
        super(userId, email, password);
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

    public Card getMetroCard()
    {
        return metroCard;
    }

    public void setMetroCard(Card metroCard)
    {
        this.metroCard = metroCard;
    }

    @Override
    public String toString()
    {
        return "Passenger{" +
                "metroCard=" + metroCard +
                "} " + super.toString();
    }
}
