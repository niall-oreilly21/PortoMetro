package com.metroporto.enums;

public enum TrainModel
{
    EURO_TRAM("Eurotram"),
    LRVs("Flexity Swift LRVs");

    private final String label;

    private TrainModel(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
}