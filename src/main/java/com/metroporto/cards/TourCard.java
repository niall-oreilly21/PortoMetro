package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TourCard extends BlueCard
{
    public TourCard(int cardId, CardAccessType cardAccessType, double cardPrice, int numberOfTrips)
    {
        super(cardId, cardAccessType, cardPrice, numberOfTrips);
    }

    public TourCard(CardAccessType cardAccessType, double cardPrice, int numberOfTrips)
    {
        super(cardAccessType, cardPrice, numberOfTrips);
    }
}
