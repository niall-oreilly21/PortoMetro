package com.metroporto;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.enums.*;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumLabelConverterTest
{
    private EnumLabelConverter enumLabelConverter;

    @BeforeEach
    void setUp()
    {
        enumLabelConverter = new EnumLabelConverter();
    }

    @Test
    void enumLabelConverterTimetableType()
    {
        System.out.println("\nEnumLabelConverter converts 'Saturday' String to TimetableType enum");

        String label = "saturday";

        TimeTableType expected = TimeTableType.SATURDAY;
        TimeTableType actual = enumLabelConverter.fromLabel(label, TimeTableType.class);
        assertEquals(expected,actual);
    }

    @Test
    void enumLabelConverterTrainModelType()
    {
        System.out.println("\nEnumLabelConverter converts 'Eurotram' String to TimetableType enum");

        String label = "Eurotram";

        TrainModel expected = TrainModel.EURO_TRAM;
        TrainModel actual = enumLabelConverter.fromLabel(label, TrainModel.class);
        assertEquals(expected,actual);
    }

    @Test
    void enumLabelConverterPage()
    {
        System.out.println("\nEnumLabelConverter converts 'administrator' String to Page enum");

        String label = "administrator";

        Page expected = Page.ADMINISTRATOR;
        Page actual = enumLabelConverter.fromLabel(label, Page.class);
        assertEquals(expected,actual);
    }

    @Test
    void enumLabelConverterHomeMenuOptions()
    {
        System.out.println("\nEnumLabelConverter converts 'Look up Metro schedules by line' String to HomeMenuOption enum");

        String label = "Look up Metro schedules by line";

        HomeMenuOption expected = HomeMenuOption.SCHEDULE_OPTION;
        HomeMenuOption actual = enumLabelConverter.fromLabel(label, HomeMenuOption.class);
        assertEquals(expected,actual);
    }

    @Test
    void enumLabelConverterFolder()
    {
        System.out.println("\nEnumLabelConverter converts 'ordercard' String to Folder enum");

        String label = "ordercard";

        Folder expected = Folder.ORDER_CARD;
        Folder actual = enumLabelConverter.fromLabel(label, Folder.class);
        assertEquals(expected,actual);
    }
}
