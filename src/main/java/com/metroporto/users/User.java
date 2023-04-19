package com.metroporto.users;

public abstract class User
{
    protected int userId;
    protected final String email;
    protected String password;

    public User(int userId, String email, String password)
    {
        this.userId = userId;
        this.email = email;
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

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
