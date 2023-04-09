package com.metroporto;

import java.time.LocalDateTime;
import java.util.Arrays;

public class RouteTest
{
    private Leg[] legs;

    public Leg[] getLegs()
    {
        return legs;
    }

    @Override
    public String toString()
    {
        return "Route{" +
                "legs=" + Arrays.toString(legs) +
                '}';
    }

    public static class Leg
    {
        private Step[] steps;

        public Step[] getSteps()
        {
            return steps;
        }

        @Override
        public String toString()
        {
            return "Leg{" +
                    "steps=" + Arrays.toString(steps) +
                    '}';
        }
    }

    public static class Time {
        private String text;
        private long value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
    public static class Step
    {
        private TransitDetails transit_details;

        public TransitDetails getTransit_details()
        {
            return transit_details;
        }

        @Override
        public String toString()
        {
            return "Step{" +
                    "transit_details=" + transit_details +
                    '}';
        }
    }

    public static class TransitDetails
    {
        private ArrivalTime arrival_time;
        private DepartureTime departure_time;
        private Stop arrival_stop;
        private Stop departure_stop;
        private Line line;
        private Time[] arrival_times;
        private Stop[] stops;

        public Time[] getArrival_times() {
            return arrival_times;
        }

        public Stop[] getStops()
        {
            return stops;
        }

        public ArrivalTime getArrival_time()
        {
            return arrival_time;
        }

        public DepartureTime getDeparture_time()
        {
            return departure_time;
        }

        public Stop getArrival_stop()
        {
            return arrival_stop;
        }

        public Stop getDeparture_stop()
        {
            return departure_stop;
        }

        public Line getLine()
        {
            return line;
        }

        @Override
        public String toString()
        {
            return "TransitDetails{" +
                    "arrival_time=" + arrival_time +
                    ", departure_time=" + departure_time +
                    ", arrival_stop=" + arrival_stop +
                    ", departure_stop=" + departure_stop +
                    ", line=" + line +
                    '}';
        }
    }

    public static class ArrivalTime
    {
        private String text;
        private String time_zone;
        private long value;

        public String getText()
        {
            return text;
        }

        public String getTime_zone()
        {
            return time_zone;
        }

        public long getValue()
        {
            return value;
        }

        @Override
        public String toString()
        {
            return "ArrivalTime{" +
                    "text='" + text + '\'' +
                    ", time_zone='" + time_zone + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    public static class DepartureTime
    {
        private String text;
        private String time_zone;
        private long value;

        public String getText()
        {
            return text;
        }

        public String getTime_zone()
        {
            return time_zone;
        }

        public long getValue()
        {
            return value;
        }

        @Override
        public String toString()
        {
            return "DepartureTime{" +
                    "text='" + text + '\'' +
                    ", time_zone='" + time_zone + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    public static class Stop
    {
        private String name;
        private ArrivalTime arrivalTime;

        public String getName()
        {
            return name;
        }

        @Override
        public String toString()
        {
            return "Stop{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public ArrivalTime getArrival_time()
        {
            return arrivalTime;
        }
    }

    public static class Line
    {
        private String short_name;

        public String getShort_name()
        {
            return short_name;
        }

        @Override
        public String toString()
        {
            return "Line{" +
                    "short_name='" + short_name + '\'' +
                    '}';
        }
    }
}
