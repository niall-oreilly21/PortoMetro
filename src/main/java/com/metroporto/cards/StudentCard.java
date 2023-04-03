package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDateTime;

public class StudentCard extends GreyCard
{
    public StudentCard(int cardId, boolean isActive, CardAccessType accessType, double cardPrice, LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        super(cardId, isActive, accessType, cardPrice, startDateTime, endDateTime);
    }
}
