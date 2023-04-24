package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDateTime;

public class GreyCard extends Card
{
    private LocalDateTime endDateTime;

    public GreyCard(int cardId, CardAccessType accessType, double cardPrice, LocalDateTime endDateTime)
    {
        super(cardId, accessType, cardPrice);
        this.endDateTime = endDateTime;
        checkExpiration();
    }

    @Override
    protected void checkExpiration()
    {
        LocalDateTime now = LocalDateTime.now();
        isActive = endDateTime.isAfter(now);
    }

    @Override
    public String toString()
    {
        return "GreyCard{" +
                "endDateTime=" + endDateTime +
                "} " + super.toString();
    }
}