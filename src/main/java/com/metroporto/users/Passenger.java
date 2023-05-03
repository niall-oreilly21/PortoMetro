package com.metroporto.users;

import com.metroporto.metro.JourneyPlanner;
import com.metroporto.cards.Card;
import com.metroporto.cards.StudentCard;

import java.util.HashSet;
import java.util.Set;

public class Passenger extends User
{
    private Card metroCard;
    private Set<JourneyPlanner> journeyPlanners;

    public Passenger(int userId, String email, String password, String firstName, String lastName, Card metroCard)
    {
        super(userId, email, password, firstName, lastName);
        checkMetroCard(metroCard);
        this.journeyPlanners = new HashSet<>();
    }

    public Passenger(int userId, String email, String password, String firstName, String lastName)
    {
        super(userId, email, password, firstName, lastName);
        this.metroCard = null;
        this.journeyPlanners = new HashSet<>();
    }

    public Passenger(String email, String firstName, String lastName, Card metroCard)
    {
        super(email, firstName,  lastName);
        checkMetroCard(metroCard);
        this.journeyPlanners = new HashSet<>();
    }

    public Passenger(String email, String firstName, String lastName)
    {
        super(email, firstName,  lastName);
        this.metroCard = null;
        this.journeyPlanners = new HashSet<>();
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

    public boolean addJourneyPlanner(JourneyPlanner journeyPlannerToBeAdded)
    {
        return journeyPlanners.add(journeyPlannerToBeAdded);
    }

    public Set<JourneyPlanner> getJourneyPlanners()
    {
        return journeyPlanners;
    }

    @Override
    public String toString()
    {
        return "Passenger{" +
                "metroCard=" + metroCard +
                "} " + super.toString();
    }
}
