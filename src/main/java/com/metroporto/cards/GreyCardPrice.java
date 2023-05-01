package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

public class GreyCardPrice extends CardPrice
{
    private double monthlyTopUpPrice;

    public GreyCardPrice(int cardPriceId, double physicalCardPrice, double monthlyTopUpPrice)
    {
        super(cardPriceId, physicalCardPrice);
        this.monthlyTopUpPrice = monthlyTopUpPrice;
    }

    @Override
    public String toString()
    {
        return "GreyCardPrice{" +
                "monthlyTopUpPrice=" + monthlyTopUpPrice +
                "} " + super.toString();
    }
}
