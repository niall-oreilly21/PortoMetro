package com.metroporto.enums;

public enum HomeMenuOption implements LabelInterface
{
    SCHEDULE_OPTION("Look up Metro schedules by line"),
    JOURNEY_ROUTE_OPTION("Plan your journey or view saved routes"),
    STATION_OPTION("Look up station(s) information"),
    PROFILE_OPTION("Edit my profile"),
    CARD_OPTION("View my card details");

    private final String label;

    private HomeMenuOption(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
