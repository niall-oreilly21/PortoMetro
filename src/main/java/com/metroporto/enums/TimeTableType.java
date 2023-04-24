package com.metroporto.enums;

public enum TimeTableType implements LabelInterface
{
    MONDAY_TO_FRIDAY("Monday-Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

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
