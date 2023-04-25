package com.metroporto.users;

public abstract class User
{
    private int userId;
    private final String email;
    private String password;
    private String firstName;
    private String lastName;

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

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
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
