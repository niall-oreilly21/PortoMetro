package com.metroporto.cards;

import com.metroporto.metro.Zone;

import java.sql.Time;

public class BlueCard extends Card
{
    private int numberOfTrips;
    private Zone zone;

    public BlueCard(int cardID, Time durationOfCard, int numberOfTrips)
    {
        super(cardID, durationOfCard);
        this.numberOfTrips = numberOfTrips;
    }
}
