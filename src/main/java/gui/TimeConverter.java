package gui;

import javafx.util.StringConverter;

public class TimeConverter extends StringConverter<Integer>
{
    @Override
    public String toString(Integer value) {
        if (value == null) {
            return "";
        }
        return String.format("%02d", value);
    }

    @Override
    public Integer fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }
}
