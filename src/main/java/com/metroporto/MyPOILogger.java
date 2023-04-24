package com.metroporto;

import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.util.ArrayList;
import java.util.List;

public class MyPOILogger
{

    public static void main(String[] args)
    {
        User user1 = new Passenger(0,"","",null);
        User userTwo = new Student(0,"","",null, null);

        List<User>users = new ArrayList<>();
        //users.add(user1);
        users.add(userTwo);

        String userType = "";

        for (User user : users)
        {

            if(user instanceof Passenger)
            {
                if(user instanceof Student)
                {
                    userType = "student";
                }
                else
                {
                    userType = "passenger";
                }
            }
            else
            {
                userType = "administrator";
            }

        }
        System.out.println(userType);
    }
}
