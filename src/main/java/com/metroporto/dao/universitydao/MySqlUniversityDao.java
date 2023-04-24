package com.metroporto.dao.universitydao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUniversityDao extends MySqlDao<University> implements UniversityDaoInterface
{
    @Override
    public List<University> findAll() throws DaoException
    {
        List<University> universities = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM universities";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                universities.add(createDto());
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findAll() in MySqlUniversityDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findAll() in MySqlUniversityDao");
        }

        return universities;
    }

    @Override
    public University findUniversityByUniversityId(String universityIdToBeFound) throws DaoException
    {
        University university = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM universities WHERE university_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, universityIdToBeFound);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                university = createDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findUniversityByUniversityId() in MySqlUniversityDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findUniversityByUniversityId() in MySqlUniversityDao()");
        }

        return university;
    }

    @Override
    protected University createDto() throws SQLException
    {
        String universityId = rs.getString("university_id");
        String universityName = rs.getString("university_name");

        return new University(universityId, universityName);
    }
}
