//package com.metroporto.dao.createdatabase;
//
//import com.metroporto.dao.MySqlDao;
//import com.metroporto.exceptions.DaoException;
//import com.metroporto.metro.University;
//import org.apache.ibatis.jdbc.ScriptRunner;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.sql.*;
//
///*Run this file to create database*/
//
//public class CreateMySqlPortoMetroDataBase extends MySqlDao<String> implements CreatePortoMetroDataBaseInterface
//{
//
//    @Override
//    public void runSqlFile(String filePath) throws DaoException
//    {
//        try
//        {
//            //createDatabase();
//
//            // Load the SQL script from file
//            con = this.getConnection();
//            Reader reader = new BufferedReader(new FileReader(filePath));
//
//
//            // Create ScriptRunner without setting custom delimiter
//            ScriptRunner scriptRunner = new ScriptRunner(con);
//            scriptRunner.setDelimiter(";"); // Set your custom delimiter here
//            scriptRunner.setStopOnError(true);
//
//            // Run the SQL script
//            scriptRunner.runScript(reader);
//
//            // Close resources
//            reader.close();
//
//            System.out.println("SQL script executed successfully.");
//
//        }
//        catch (SQLException | IOException sqe)
//        {
//            throw new DaoException("runSqlFile() " + sqe.getMessage());
//        }
//        finally
//        {
//            handleFinally("runSqlFile()");
//        }
//    }
//
//    @Override
//    protected String createDto() throws SQLException
//    {
//        return null;
//    }
//
////    @Override
////    public void createDatabase() throws DaoException
////    {
////        try
////        {
////            con = this.getConnectionToServer();
////            String query = "CREATE DATABASE IF NOT EXISTS " + databaseName;
////            ps = con.prepareStatement(query);
////
////            //Use the prepared statement to execute the sql
////            ps.executeUpdate();
////
////        }
////        catch (SQLException sqe)
////        {
////            throw new DaoException("createDatabase() " + sqe.getMessage());
////        }
////        finally
////        {
////            handleFinally("createDatabase()");
////        }
////    }
////
////    @Override
////    public boolean checkDatabaseExists() throws DaoException
////    {
////        boolean databaseExists = false;
////
////        try
////        {
////            //Get a connection to the database
////            con = this.getConnectionToServer();
////
////            String query = "SELECT SCHEMA_NAME " +
////                    "FROM INFORMATION_SCHEMA.SCHEMATA " +
////                    "WHERE SCHEMA_NAME = ?";
////
////            ps = con.prepareStatement(query);
////            ps.setString(1, databaseName);
////
////            //Use the prepared statement to execute the sql
////            rs = ps.executeQuery();
////
////            if (rs.next())
////            {
////                System.out.println("Database exists.");
////                databaseExists = true;
////            }
////            else
////            {
////                System.out.println("Database does not exist.");
////                databaseExists = false;
////            }
////        }
////        catch (SQLException sqe)
////        {
////            throw new DaoException("checkDatabaseExists() " + sqe.getMessage());
////        }
////        finally
////        {
////            handleFinally("checkDatabaseExists()");
////        }
////
////        return databaseExists;
////    }
//}
