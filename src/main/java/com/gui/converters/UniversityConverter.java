package com.gui.converters;

import com.metroporto.metro.University;
import javafx.util.StringConverter;

public class UniversityConverter extends StringConverter<University>
{
    @Override
    public String toString(University university)
    {
        return university.getUniversityName();
    }

    @Override
    public University fromString(String s)
    {
        return null;
    }
}
