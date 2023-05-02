package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

public class BlueCard extends Card
{
    private int totalTrips;

    public BlueCard(String cardId, CardAccessType cardAccessType, CardPrice cardPrice, int totalTrips)
    {
        super(cardId, cardAccessType, cardPrice);
        this.totalTrips = totalTrips;
        checkExpiration();
    }

    public BlueCard(CardAccessType cardAccessType, CardPrice cardPrice, int totalTrips)
    {
        super(cardAccessType, cardPrice);
        this.totalTrips = totalTrips;
        checkExpiration();
    }


    public int getTotalTrips()
    {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips)
    {
        this.totalTrips = totalTrips;
    }

    @Override
    protected void checkExpiration()
    {
        isActive = totalTrips > 0;
    }

    @Override
    public String toString()
    {
        return "BlueCard{" +
                "numberOfTrips=" + totalTrips +
                "} " + super.toString();
    }
}
