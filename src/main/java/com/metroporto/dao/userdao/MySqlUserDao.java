package com.metroporto.dao.userdao;

import com.metroporto.cards.Card;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.metro.Zone;
import com.metroporto.users.Administrator;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao extends MySqlDao<User> implements UserDaoInterface
{
    private UniversityDaoInterface universityDao;
    private CardDaoInterface cardDao;

    public MySqlUserDao()
    {
        this.universityDao = new MySqlUniversityDao();
        this.cardDao = new MySqlCardDao();
    }

    @Override
    public List<User> findAll() throws DaoException
    {
        List<User> users = new ArrayList<>();

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * from all_users";
            ps = con.prepareStatement(query);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                users.add(createDto());
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAll() in MySqlUserDao " + sqe.getMessage());
        } finally
        {
            handleFinally("findAll() in MySqlUserDao");
        }

        return users;
    }

    @Override
    protected User createDto() throws SQLException
    {
        User user = null;
        int userId = rs.getInt("user_id");
        String email = rs.getString("email");
        String password = rs.getString("user_password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String userType = rs.getString("user_type");

        if(userType.equalsIgnoreCase("student") || userType.equalsIgnoreCase("passenger"))
        {

            Card card = cardDao.findCardByCardId(userId);

            if(userType.equalsIgnoreCase("student"))
            {
                String universityId = rs.getString("university_id");

                University university = universityDao.findUniversityByUniversityId(universityId);

                user = new Student(userId, email, password, firstName, lastName, card, university);
            }
            else
            {
                user = new Passenger(userId, email, password, firstName, lastName, card);
            }
        }
        else if(userType.equalsIgnoreCase("administrator"))
        {
            user = new Administrator(userId, email, password, firstName, lastName);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws DaoException
    {
        User user = null;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "SELECT * FROM all_users WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            //Use the prepared statement to execute the sql
            rs = ps.executeQuery();

            while (rs.next())
            {
                user = createDto();
            }
        }
        catch (SQLException sqe)
        {
            throw new DaoException("findUserByEmail() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("findUserByEmail() in MySqlUserDao");
        }

        return user;
    }

    @Override
    public boolean insertUser(User user) throws DaoException
    {
        boolean isInserted = false;

        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "INSERT INTO users (email, user_password, first_name, last_name, user_type) VALUES\n" +
                    "(?, ?, ?, ?, ?)";

            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());

            String userType;


            if(user instanceof Passenger)
            {
                if(user instanceof Student)
                {
                    userType = "student";
                }
                else
                {
                    userType = "passenger";
                }
            }
            else
            {
                userType = "administrator";
            }

            ps.setString(5, userType);


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                isInserted = true;

                // Retrieve the generated keys
                rs = ps.getGeneratedKeys();

                if (rs.next())
                {
                    int userId = rs.getInt(1);
                    user.setUserId(userId);
                }
            }
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("insertUser() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
           handleFinally("insertUser() in MySqlUserDao");
        }

        return isInserted;
    }

    @Override
    public boolean updateEmail(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "UPDATE users SET email = ? WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                return true;
            }

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("updateEmail() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("updateEmail() in MySqlUserDao");
        }

        return false;
    }

    @Override
    public boolean updatePassword(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "UPDATE users SET user_password = ? WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                return true;
            }

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("updatePassword() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("updatePassword() in MySqlUserDao");
        }

        return false;
    }

    @Override
    public boolean updateFirstName(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "UPDATE users SET first_name = ? WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                return true;
            }

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("updateFirstName() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("updateFirstName() in MySqlUserDao");
        }

        return false;
    }

    @Override
    public boolean updateLastName(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            query = "UPDATE users SET last_name = ? WHERE user_id = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, user.getLastName());
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                return true;
            }

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            // Handle duplicate entry error
            System.out.println("Duplicate entry found in the database");
        }
        catch (SQLException sqe)
        {
            throw new DaoException("updateLastName() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("updateLastName() in MySqlUserDao");
        }

        return false;
    }


    @Override
    public boolean remove(User user) throws DaoException
    {
        try
        {
            //Get a connection to the database
            con = this.getConnection();
            String query = "DELETE FROM users WHERE user_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user.getUserId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                query = "SELECT * FROM users WHERE user_id = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, user.getUserId());
                rs = ps.executeQuery();

                if (!rs.next())
                {
                    return true;
                }
            }

        }
        catch (SQLException sqe)
        {
            throw new DaoException("remove() in MySqlUserDao " + sqe.getMessage());
        }
        finally
        {
            handleFinally("remove() in MySqlUserDao");
        }

        return false;
    }

}
