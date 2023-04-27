package com.metroporto.users;

import com.metroporto.users.User;

public class Administrator extends User
{
    public Administrator(int userId, String email, String password, String firstName, String lastName)
    {
        super(userId, email, password, firstName, lastName);
    }

    @Override
    public String toString()
    {
        return "Administrator{} " + super.toString();
    }
}
