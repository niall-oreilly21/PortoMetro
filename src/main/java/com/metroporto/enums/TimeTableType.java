package com.metroporto.enums;

public enum TimeTableType implements LabelInterface
{
    MONDAY_TO_FRIDAY("Monday-Friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String label;

    private TimeTableType(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
