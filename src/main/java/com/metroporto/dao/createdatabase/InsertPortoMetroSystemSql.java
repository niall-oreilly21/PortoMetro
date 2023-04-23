//package com.metroporto.dao.createdatabase;
//
//import com.metroporto.dao.createdatabase.CreateMySqlPortoMetroDataBase;
//import com.metroporto.dao.createdatabase.CreatePortoMetroDataBaseInterface;
//import com.metroporto.exceptions.DaoException;
//
///*run this file to insert the database*/
//
//public class InsertPortoMetroSystemSql
//{
//    public static  void main(String[] args)
//    {
//
//        CreatePortoMetroDataBaseInterface database = new CreateMySqlPortoMetroDataBase();
//
//        try
//        {
//            database.runSqlFile("porto_metro_system.sql");
//        }
//        catch (DaoException de)
//        {
//            System.out.println(de.getMessage());
//        }
//
//
//    }
//}
