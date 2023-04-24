package com.metroporto.cards;
import com.metroporto.enums.CardAccessType;
import com.metroporto.metro.Zone;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Card
{
    private int cardId;
    protected boolean isActive;
    private CardAccessType cardAccessType;
    private double cardPrice;
    private List<Zone> zones;
    private static final int THREE_ZONES_SIZE = 3;

    public Card(int cardId, CardAccessType cardAccessType, double cardPrice)
    {
        this.cardId = cardId;
        this.cardAccessType = cardAccessType;
        this.cardPrice = cardPrice;

        if(this.cardAccessType.equals(CardAccessType.THREE_ZONES))
        {
            this.zones = new ArrayList<>(THREE_ZONES_SIZE);
        }
        else
        {
            this.zones = null;
        }
    }

    public int getCardId()
    {
        return cardId;
    }

    public void setCardId(int cardId)
    {
        this.cardId = cardId;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public CardAccessType getAccessType()
    {
        return cardAccessType;
    }

    public void setAccessType(CardAccessType accessType)
    {
        this.cardAccessType = accessType;
    }

    public double getCardPrice()
    {
        return cardPrice;
    }

    public void setCardPrice(double cardPrice)
    {
        this.cardPrice = cardPrice;
    }

    public void addZone(Zone zone)
    {
        zones.add(zone);
    }

    protected void checkExpiration()
    {
        this.isActive = false;
    }

    @Override
    public String toString()
    {
        return "Card{" +
                "cardId=" + cardId +
                ", isActive=" + isActive +
                ", cardAccessType=" + cardAccessType +
                ", cardPrice=" + cardPrice +
                ", zones=" + zones +
                '}';
    }
}
