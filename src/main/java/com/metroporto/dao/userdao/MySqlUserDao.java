package com.metroporto.dao.userdao;

import com.metroporto.cards.Card;
import com.metroporto.dao.MySqlDao;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Station;
import com.metroporto.metro.University;
import com.metroporto.metro.Zone;
import com.metroporto.users.Administrator;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao extends MySqlDao implements UserDaoInterface
{
    private UniversityDaoInterface universityDao;
    private CardDaoInterface cardDao;

    public MySqlUserDao()
    {
        this.universityDao = new MySqlUniversityDao();
        this.cardDao = new MySqlCardDao();
    }

    @Override
    public List<User> findAllUsers() throws DaoException
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
                int userId = rs.getInt("user_id");
                String email = rs.getString("email");
                String password = rs.getString("user_password");
                String userType = rs.getString("user_type");

                if(userType.equalsIgnoreCase("student") || userType.equalsIgnoreCase("passenger"))
                {
                    int cardId = rs.getInt("card_id");

                    Card card = cardDao.findCardByCardId(cardId);

                    if(userType.equalsIgnoreCase("student"))
                    {
                        String universityId = rs.getString("university_id");

                        University university = universityDao.findUniversityByUniversityId(universityId);

                        users.add(new Student(userId, email, password, card, university));
                    }
                    else
                    {
                        users.add(new Passenger(userId, email, password, card));
                    }

                }
                else if(userType.equalsIgnoreCase("administrator"))
                {
                    users.add(new Administrator(userId, email, password));
                }
            }
        } catch (SQLException sqe)
        {
            throw new DaoException("findAllUsers() " + sqe.getMessage());
        } finally
        {
            handleFinally("findAllUsers()");
        }

        return users;
    }
}
