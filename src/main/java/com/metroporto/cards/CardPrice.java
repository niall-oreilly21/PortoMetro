package com.metroporto.cards;

public class CardPrice
{
    private int cardPriceId;
    private double physicalCardPrice;
    private double topUpPrice;

    public CardPrice(int cardPriceId, double physicalCardPrice, double topUpPrice)
    {
        this.cardPriceId = cardPriceId;
        this.physicalCardPrice = physicalCardPrice;
        this.topUpPrice = topUpPrice;
    }

    public int getCardPriceId()
    {
        return cardPriceId;
    }

    public double getPhysicalCardPrice()
    {
        return physicalCardPrice;
    }

    public double getTopUpPrice()
    {
        return topUpPrice;
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
