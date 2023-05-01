package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;
import com.metroporto.metro.Zone;

import java.util.ArrayList;
import java.util.List;

public abstract class CardPrice
{
    private int cardPriceId;
    private double physicalCardPrice;

    public CardPrice(int cardPriceId, double physicalCardPrice)
    {
        this.cardPriceId = cardPriceId;
        this.physicalCardPrice = physicalCardPrice;
    }

    @Override
    public String toString()
    {
        return "CardPrice{" +
                "cardPriceId=" + cardPriceId +
                ", physicalCardPrice=" + physicalCardPrice +
                '}';
    }
}
