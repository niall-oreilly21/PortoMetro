package com.metroporto.users;

public abstract class User
{
    protected final int userId;
    protected final String email;
    protected final String password;

    public User(int userID, String username, String password)
    {
        this.userId = userID;
        this.email = username;
        this.password = password;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}
