package com.metroporto.cards;
import java.sql.Time;

public abstract class Card
{
    private final int cardID;
    private Time durationOfCard;

    public Card(int cardID, Time durationOfCard)
    {
        this.cardID = cardID;
        this.durationOfCard = calculateTimeLeftOnCard();
    }

    private Time calculateTimeLeftOnCard()
    {
        return null;
    }
}
