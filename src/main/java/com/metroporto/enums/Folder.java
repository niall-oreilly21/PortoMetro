package com.metroporto.enums;


public enum Folder implements LabelInterface
{
    ACCOUNT_AUTH("accountauth"),
    HOME("home"),
    ORDER_CARD("ordercard");

    private final String label;

    private Folder(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}

