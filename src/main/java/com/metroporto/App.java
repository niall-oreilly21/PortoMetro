package com.metroporto;


import com.metroporto.cards.GreyCard;
import com.metroporto.enums.CardAccessType;

import java.time.LocalDateTime;

public class App
{
    public static void main(String[] args)
    {
        GreyCard card = new GreyCard(1, CardAccessType.ALL_ZONES, 12, LocalDateTime.of(2025, 12,12,12,12,12));

        System.out.println(card.isActive());
    }
}











