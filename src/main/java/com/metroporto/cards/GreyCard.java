package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.time.LocalDate;

public class GreyCard extends Card
{
    private LocalDate startDate;
    private LocalDate endDate;

    public GreyCard(String cardId, CardAccessType accessType, CardPrice cardPrice, LocalDate startDate, LocalDate endDate)
    {
        super(cardId, accessType, cardPrice);
        this.startDate = startDate;
        this.endDate = endDate;
        checkExpiration();
    }

    public GreyCard(LocalDate startDate, LocalDate endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        checkExpiration();
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    @Override
    protected void checkExpiration()
    {
        LocalDate now = LocalDate.now();
        isActive = (now.isAfter(startDate)) && (now.isBefore(endDate));
    }

    @Override
    public String toString()
    {
        return "GreyCard{" +
                "endDateTime=" + endDate +
                "} " + super.toString();
    }
}
