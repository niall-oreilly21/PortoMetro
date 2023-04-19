package com.metroporto.dao.universitydao;

import com.metroporto.dao.MySqlDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;

import java.sql.SQLException;

public class MySqlUniversityDao extends MySqlDao implements UniversityDaoInterface
{
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
                String universityId = rs.getString("university_id");
                String universityName = rs.getString("university_name");

                university = new University(universityId, universityName);
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findUniversityByUniversityId() " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findUniversityByUniversityId()");
        }

        return university;
    }
}
