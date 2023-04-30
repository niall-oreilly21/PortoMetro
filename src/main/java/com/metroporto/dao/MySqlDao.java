package com.metroporto.dao;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class MySqlDao<T>
{
    protected Connection con;
    protected PreparedStatement ps;
    protected ResultSet rs;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/";
    protected static final String databaseName = "porto_metro_system";
    protected static final String username = "root";
    private static final String password = "";
    protected String query;
    protected EnumLabelConverter enumLabelConverter = new EnumLabelConverter();
    private static final HashMap<String, Station> stations = new HashMap<>();

    public MySqlDao()
    {
        this.con = null;
        this.ps = null;
        this.rs = null;
        this.query = "";
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

    private static BasicDataSource dataSource = new BasicDataSource();

    static
    {
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url + databaseName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(1000); // maximum number of connections
        dataSource.setMinIdle(5); // minimum number of idle connections
    }

    protected Connection getConnection() throws DaoException
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException e)
        {
            throw new DaoException("Failed to get a database connection: " + e.getMessage());
        }

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


    protected Station getCachedStation(String stationId, StationDaoInterface stationDao) throws DaoException
    {
        Station station;

        if (stations.containsKey(stationId))
        {
            return stations.get(stationId);
        }
        else
        {
            // Fetch station data from database and add it to cache
            station = fetchStationDto(stationId,stationDao);
            stations.put(stationId, station);
        }

        return station;
    }

    private Station fetchStationDto(String stationId, StationDaoInterface stationDao) throws DaoException
    {
        return stationDao.findStationByStationId(stationId);
    }

}
