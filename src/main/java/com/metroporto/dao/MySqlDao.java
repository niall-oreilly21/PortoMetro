package com.metroporto.dao;

import com.metroporto.exceptions.DaoException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDao
{
    protected Connection con;

    public MySqlDao()
    {
        con = null;
    }
    public Connection getConnection() throws DaoException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/porto_metro_system";
        String username = "root";
        String password = "";
        Connection con = null;

        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Failed to find the driver class " + cnfe.getMessage());
        }
        catch (SQLException sqe)
        {
            System.out.println("Connection failed " + sqe.getMessage());
        }
        return con;

    }

    public void freeConnection(Connection con) throws DaoException
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

    public void createDatabaseIfNotExists() throws DaoException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        String databaseName = "porto_metro_system";
        String sqlFilePath = "/path/to/sqlfile.sql"; // Specify the path to your SQL file here

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);

            // Check if the database exists
            Statement stmt = conn.createStatement();
            String checkDatabaseQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
                    + databaseName + "'";
            boolean databaseExists = false;
            try {
                stmt.execute(checkDatabaseQuery);
                databaseExists = true;
            } catch (SQLException e) {
                // Database does not exist
            }

            stmt.close();
            conn.close();

            if (!databaseExists) {
                // Run SQL file to create the database
                conn = getConnection();
                stmt = conn.createStatement();

                // Read the SQL file
                File sqlFile = new File(sqlFilePath);
                BufferedReader br = new BufferedReader(new FileReader(sqlFile));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                // Split the SQL file content into individual queries
                String[] queries = sb.toString().split(";");

                // Execute each query
                for (String query : queries) {
                    stmt.execute(query);
                }

                stmt.close();
                conn.close();
            }

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
