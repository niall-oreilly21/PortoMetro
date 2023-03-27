package com.metroporto;

import com.metroporto.cards.GreyCard;
import com.metroporto.cards.StudentCard;
import com.metroporto.users.Passenger;

import java.sql.Time;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Passenger passenger = new Passenger(12345,"niall","passwprd", "Niall O' Reilly", "niall.blackrock@gmail.com","123456789",new GreyCard(3434, new Time(12,12,12)));

        System.out.println(passenger.getEmail());
    }
}
