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
    private CardPrice cardPrice;
    private List<Zone> zones;
    private static final int threeZonesSize = 3;

    public Card(int cardId, CardAccessType cardAccessType, CardPrice cardPrice)
    {
        this.cardId = cardId;
        this.cardAccessType = cardAccessType;
        this.cardPrice = cardPrice;
        this.isActive = false;
        setZones();
    }

    public Card(CardAccessType cardAccessType, CardPrice cardPrice)
    {
        this.cardId = 0;
        this.cardAccessType = cardAccessType;
        this.cardPrice = cardPrice;
        this.isActive = false;
        setZones();
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

    private void setZones()
    {
        if(this.cardAccessType.equals(CardAccessType.THREE_ZONES))
        {
            this.zones = new ArrayList<>(threeZonesSize);
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
