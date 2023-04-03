package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDateTime;

public class GreyCard extends Card
{
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public GreyCard(int cardId, boolean isActive, CardAccessType accessType, double cardPrice, LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        super(cardId, isActive, accessType, cardPrice);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
