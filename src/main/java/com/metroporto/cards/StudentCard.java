package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.time.LocalDate;

public class StudentCard extends GreyCard
{
    public StudentCard(String cardId, CardAccessType accessType, CardPrice cardPrice, LocalDate startDate, LocalDate endDate)
    {
        super(cardId, accessType, cardPrice, startDate, endDate);
    }

    public StudentCard(LocalDate startDate, LocalDate endDate)
    {
        super(startDate, endDate);
    }
}
