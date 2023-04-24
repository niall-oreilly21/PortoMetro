package com.metroporto.dao.linedao;

import com.metroporto.dao.FindAllDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Line;

public interface LineDaoInterface extends FindAllDaoInterface<Line>
{
    Line findLineByLineId(String lineId) throws DaoException;
}
