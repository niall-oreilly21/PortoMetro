package com.metroporto;

import com.metroporto.*;
import com.metroporto.cards.GreyCard;
import com.metroporto.users.Passenger;
import com.metroporto.users.User;

import java.sql.Time;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        User passenger = new Passenger(12345,"niall","passwprd", new GreyCard(3434, new Time(12,12,12)));

        System.out.println(passenger.getEmail());
    }
}
