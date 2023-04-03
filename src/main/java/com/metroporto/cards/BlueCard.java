package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;
import com.metroporto.metro.Zone;

import java.sql.Time;

public class BlueCard extends Card
{
    private int numberOfTrips;

    public BlueCard(int cardId, boolean isActive, CardAccessType cardAccessType, double cardPrice, int numberOfTrips)
    {
        super(cardId, isActive, cardAccessType, cardPrice);
        this.numberOfTrips = numberOfTrips;
    }

    public int getNumberOfTrips()
    {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips)
    {
        this.numberOfTrips = numberOfTrips;
    }
}
