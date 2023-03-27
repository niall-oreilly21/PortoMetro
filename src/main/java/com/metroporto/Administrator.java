package com.metroporto;

public class Administrator extends User
{
    private int adminID;

    public Administrator(int userID, String username, String password, int adminID)
    {
        super(userID, username, password);
        this.adminID = adminID;
    }

    public int getAdminID()
    {
        return adminID;
    }
}
