package com.metroporto.users;

import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class User
{
    private int userId;
    private final String email;
    private String password;
    private String firstName;
    private String lastName;

    public User(int userId, String email, String password, String firstName, String lastName)
    {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password, String firstName, String lastName)
    {
        this.userId = 0;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
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

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setPassword(String password)
    {
        int cost = 4;
        this.password = BCrypt.withDefaults().hashToString(cost, password.toCharArray());
    }

    public boolean checkPassword(String password)
    {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
