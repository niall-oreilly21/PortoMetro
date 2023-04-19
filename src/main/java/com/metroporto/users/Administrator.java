package com.metroporto.users;

import com.metroporto.users.User;

public class Administrator extends User
{
    public Administrator(int userId, String email, String password)
    {
        super(userId, email, password);
    }

    @Override
    public String toString()
    {
        return "Administrator{} " + super.toString();
    }
}
