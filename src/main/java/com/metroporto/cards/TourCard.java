package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDateTime;

public class TourCard extends GreyCard
{
    public TourCard(int cardId, boolean isActive, CardAccessType accessType, double cardPrice, LocalDateTime startDateTime)
    {
        super(cardId, isActive, accessType, cardPrice, startDateTime, startDateTime.plusDays(1));
    }
}
