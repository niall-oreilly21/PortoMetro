package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentCard extends GreyCard
{

    public StudentCard(int cardId, CardAccessType accessType, CardPrice cardPrice, LocalDate endDate)
    {
        super(cardId, accessType, cardPrice, endDate);
    }
}
