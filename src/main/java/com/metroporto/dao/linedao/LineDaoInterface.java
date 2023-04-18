package com.metroporto.dao.linedao;

import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;

import java.util.List;

public interface LineDaoInterface
{
    List<Line> findAllLines() throws DaoException;
}
