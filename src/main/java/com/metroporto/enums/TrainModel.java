package com.metroporto.enums;

public enum TrainModel implements LabelInterface
{
    EURO_TRAM("Eurotram"),
    LRVs("Flexity Swift LRVs");

    private final String label;

    private TrainModel(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}