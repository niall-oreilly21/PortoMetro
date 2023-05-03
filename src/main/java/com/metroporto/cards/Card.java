package com.metroporto.cards;
import com.metroporto.enums.CardAccessType;
import com.metroporto.metro.Zone;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Card
{
    private String cardId;
    protected boolean isActive;
    private CardAccessType cardAccessType;
    private CardPrice cardPrice;
    private List<Zone> zones;
    private static final int threeZonesSize = 3;

    public Card(String cardId, CardAccessType cardAccessType, CardPrice cardPrice)
    {
        this.cardId = cardId;
        this.cardAccessType = cardAccessType;
        this.cardPrice = cardPrice;
        this.isActive = false;
        zones = null;
    }

    public Card(CardAccessType cardAccessType, CardPrice cardPrice)
    {
        this.cardId = "";
        this.cardAccessType = cardAccessType;
        this.cardPrice = cardPrice;
        this.isActive = false;
        zones = null;
    }

    public Card()
    {
        this.cardId = "";
        this.cardAccessType = CardAccessType.THREE_ZONES;
        this.cardPrice = null;
        this.isActive = false;
        zones = null;
    }

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
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

    public CardAccessType getCardAccessType()
    {
        return cardAccessType;
    }

    public List<Zone> getZones()
    {
        return zones;
    }

    public void setAccessType(CardAccessType accessType)
    {
        this.cardAccessType = accessType;
    }

    public CardPrice getCardPrice()
    {
        return cardPrice;
    }

    public void setCardPrice(CardPrice cardPrice)
    {
        this.cardPrice = cardPrice;
    }

    public void setZones(List<Zone> zones)
    {
        if(this.cardAccessType.equals(CardAccessType.THREE_ZONES))
        {
            this.zones = zones;
        }
        else
        {
            this.zones = null;
        }
    }

    public void addZone(Zone zone)
    {
        zones.add(zone);
    }

    protected void checkExpiration()
    {
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
