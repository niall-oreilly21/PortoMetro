package com.metroporto.enums;

public enum Page implements LabelInterface
{
    ADMINISTRATOR("administrator"),
    CARD("card"),
    CARD_INVOICE("card_invoice"),
    CARD_ZONE("card_zone"),
    HOME("home"),
    JOURNEY_ROUTE("journey_route"),
    PASSENGER_CARD("passenger_card"),
    PROFILE("profile"),
    SCHEDULE("schedule"),
    SIGN_IN("sign_in"),
    SIGN_UP("sign_up"),
    STATION("station"),
    STUDENT_UNIVERSITY("student_university");

    private final String label;

    private Page(String label)
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
