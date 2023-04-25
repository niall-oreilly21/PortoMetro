package com.metroporto.dao.universitydao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

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
    public void insertUniversityForStudent(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "INSERT INTO students_universities (user_id, university_id) VALUES\n" +
                    "(?, ?)";

            ps = con.prepareStatement(query);
            ps.setInt(1, user.getUserId());

            if(user instanceof Student)
            {
                ps.setString(2, ((Student) user).getUniversity().getUniversityId());
            }

            //Use the prepared statement to execute the sql
            ps.executeUpdate();
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertUniversityForStudent() in MySqlUniversityDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("insertUniversityForStudent() in MySqlUniversityDao()");
        }

    }

    @Override
    protected University createDto() throws SQLException
    {
        String universityId = rs.getString("university_id");
        String universityName = rs.getString("university_name");

        return new University(universityId, universityName);
    }
}
