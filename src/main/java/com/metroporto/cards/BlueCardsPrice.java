package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

public class BlueCardsPrice extends CardPrice
{
    private double tripPrice;

    public BlueCardsPrice(int cardPriceId, double physicalCardPrice, double tripPrice)
    {
        super(cardPriceId, physicalCardPrice);
        this.tripPrice = tripPrice;
    }

    @Override
    public String toString()
    {
        return "BlueCardsPrice{" +
                "tripPrice=" + tripPrice +
                "} " + super.toString();
    }
}
