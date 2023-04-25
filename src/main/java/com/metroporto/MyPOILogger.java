package com.metroporto;

import com.metroporto.cards.Card;
import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;

import java.util.ArrayList;
import java.util.List;

public class MyPOILogger
{

    public static void main(String[] args) throws DaoException
    {

        CardDaoInterface cardDao = new MySqlCardDao();
        UniversityDaoInterface universityDao = new MySqlUniversityDao();
        UserDaoInterface userDao = new MySqlUserDao();

        Card card = cardDao.findCardByCardId(1);
        University university = universityDao.findUniversityByUniversityId("UOP");

        User user1 = new Passenger("niall 2","blah blah balh", "Jenna", "Ortega", card);
        User userTwo = new Student("niall 3","45678", "Luana", "Kinky", card, university);

//        userDao.insertUser(user1);
//        userDao.insertUser(userTwo);

        List<User>users = userDao.findAll();

        for (User user : users)
        {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
    }
}
