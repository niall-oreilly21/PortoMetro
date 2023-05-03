package com.metroporto.createdatabase;

import com.metroporto.createdatabase.importtimetables.ImportTimetables;
import com.metroporto.exceptions.DaoException;

/*run this file to insert the database*/

public class InsertPortoMetroSystemDatabase
{
    public static  void main(String[] args)
    {

        ImportTimetables importTimetables = new ImportTimetables();
        CreatePortoMetroDataBaseInterface database = new CreateMySqlPortoMetroDataBase();

        try
        {
            /*run this the the very first time only to put the database on your sql schema*/
//            if(!database.checkDatabaseExists())
//            {
//                database.createDatabase();
//            }

            database.runSqlFile("porto_metro_system.sql");
        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }

        //This import the timetables for the database
        importTimetables.start();
        System.out.println("All Data Inserted");
    }
}
