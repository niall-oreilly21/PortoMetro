package com.metroporto;

public class Passenger extends User
{
    private int passengerID;
    private String name;
    private String email;
    private String phoneNumber;
    private CardType metroCard;

    public Passenger(int userID, String username, String password, int passengerID, String name, String email, String phoneNumber, CardType metroCard)
    {
        super(userID, username, password);
        this.passengerID = passengerID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.metroCard = metroCard;
    }

    public int getPassengerID()
    {
        return passengerID;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public CardType getMetroCard()
    {
        return metroCard;
    }
}
