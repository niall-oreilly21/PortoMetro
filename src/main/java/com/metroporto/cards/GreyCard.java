package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.time.LocalDate;

public class GreyCard extends Card
{
    private LocalDate endDate;

    public GreyCard(int cardId, CardAccessType accessType, GreyCardPrice cardPrice, LocalDate endDate)
    {
        super(cardId, accessType, cardPrice);
        this.endDate = endDate;
        checkExpiration();
    }

    public GreyCard(CardAccessType accessType, GreyCardPrice cardPrice, LocalDate endDate)
    {
        super(accessType, cardPrice);
        this.endDate = endDate;
        checkExpiration();
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    @Override
    protected void checkExpiration()
    {
        LocalDate now = LocalDate.now();
        isActive = endDate.isAfter(now);
    }

    @Override
    public String toString()
    {
        return "GreyCard{" +
                "endDateTime=" + endDate +
                "} " + super.toString();
    }
}
