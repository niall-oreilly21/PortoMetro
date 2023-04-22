package com.metroporto.dao;

import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;

import java.io.*;
import java.sql.*;

public abstract class MySqlDao<T>
{
    protected Connection con;
    protected PreparedStatement ps;
    protected ResultSet rs;
    private final String driver;
    private final String url;
    protected final String databaseName;
    protected final String username;
    private final String password;
    protected String query;
    protected EnumLabelConverter enumLabelConverter;

    public MySqlDao()
    {
        this.con = null;
        this.ps = null;
        this.rs = null;
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.url = "jdbc:mysql://localhost/";
        this.databaseName = "porto_metro_system";
        this.username = "root";
        this.password = "";
        this.query = "";
        this.enumLabelConverter = new EnumLabelConverter();
    }

    private Connection establishConnection(String jdbcUrl)
    {
        try
        {
            Class.forName(driver);
            return DriverManager.getConnection(jdbcUrl, username, password);
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Failed to find the driver class " + cnfe.getMessage());
            return null;
        }
        catch (SQLException sqe)
        {
            System.out.println("Connection failed " + sqe.getMessage());
            return null;
        }
    }

    protected Connection getConnection() throws DaoException
    {
        Connection con = establishConnection(url + databaseName);
        return con;
    }

    protected Connection getConnectionToServer() throws DaoException
    {
        Connection con = establishConnection(url);
        return con;
    }

    protected void freeConnection(Connection con) throws DaoException
    {
        try
        {
            if(con != null)
            {
                con.close();
                con = null;
            }
        }
        catch (SQLException sqe)
        {
            System.out.println("Failed to  free the connection: " + sqe.getMessage());
            System.exit(1);
        }

    }

    protected void handleFinally(String methodName) throws DaoException
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
            if (ps != null)
            {
                ps.close();
            }
            if (con != null)
            {
                freeConnection(con);
            }
        } catch (SQLException sqe)
        {
            throw new DaoException(methodName + " " + sqe.getMessage());
        }
    }

    protected abstract T createDto() throws SQLException;
}
