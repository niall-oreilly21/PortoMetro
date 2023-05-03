package com.metroporto.dao;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;
import com.metroporto.metro.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MySqlLineDaoTest
{
    private LineDaoInterface lineDao;

    @BeforeEach
    void setUp()
    {
        lineDao = new MySqlLineDao();
    }

    @Test
    void findLineByLineIdNotNull() throws DaoException
    {
        System.out.println("\nfindLineByLineId() returns not null for line id A.");
        Line line = lineDao.findLineByLineId("A");
        assertNotNull(line);
    }

    @Test
    void findLineByLineIdNull() throws DaoException
    {
        System.out.println("\nfindLineByLineId() returns null for line id M.");
        Line line = lineDao.findLineByLineId("M");
        assertNull(line);
    }

}
