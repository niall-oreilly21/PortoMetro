package com.metroporto.cards;

import java.sql.Time;

public class BlueCard extends Card
{
    private int numberOfTrips;
    private Zones zone;

    public BlueCard(int cardID, Time durationOfCard, int numberOfTrips)
    {
        super(cardID, durationOfCard);
        this.numberOfTrips = numberOfTrips;
    }
}
