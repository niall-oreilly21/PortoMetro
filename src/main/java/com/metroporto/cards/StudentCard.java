package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentCard extends GreyCard
{
    public StudentCard(int cardId, CardAccessType accessType, double cardPrice, LocalDate endDateTime)
    {
        super(cardId, accessType, cardPrice, endDateTime);
    }

    public StudentCard(CardAccessType accessType, double cardPrice, LocalDate endDateTime)
    {
        super(accessType, cardPrice, endDateTime);
    }
}
