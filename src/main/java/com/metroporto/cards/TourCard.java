package com.metroporto.cards;

import com.metroporto.enums.CardAccessType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TourCard extends BlueCard
{

    public TourCard(int cardId, CardAccessType cardAccessType, BlueCardsPrice cardPrice, int totalTrips)
    {
        super(cardId, cardAccessType, cardPrice, totalTrips);
    }

    public TourCard(CardAccessType cardAccessType, BlueCardsPrice cardPrice, int totalTrips)
    {
        super(cardAccessType, cardPrice, totalTrips);
    }
}
