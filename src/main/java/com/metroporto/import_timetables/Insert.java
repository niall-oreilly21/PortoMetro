package com.metroporto.import_timetables;

import java.util.List;

public interface Insert<T>
{
    void insert(T element);
    List<T> getList();
}
