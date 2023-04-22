package com.metroporto.enums;

public enum CardAccessType implements LabelInterface
{
    ALL_ZONES("All zones"),
    THREE_ZONES("3 zones");

    private final String label;

    CardAccessType(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
