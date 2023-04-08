package com.metroporto;

import com.metroporto.enums.TimeTableType;

public class PortoMetroPdfPage
{
    private int pageIndex;
    private TimeTableType scheduleDay;
    private boolean lineC;

    public PortoMetroPdfPage()
    {
        this.pageIndex = 0;
        this.scheduleDay = TimeTableType.MONDAY_TO_FRIDAY;
        this.lineC = false;
        checkScheduleDayPage();
    }

    private void checkScheduleDayPage()
    {
        switch (pageIndex)
        {
            case 1:
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 19:
            case 20:
            case 25:
            case 26:
                setScheduleDay(TimeTableType.MONDAY_TO_FRIDAY);
                break;

            case 3:
            case 4:
            case 9:
            case 10:
            case 15:
            case 16:
            case 21:
            case 22:
            case 27:
            case 28:
                setScheduleDay(TimeTableType.SATURDAY);
                break;

            case 5:
            case 6:
            case 11:
            case 12:
            case 17:
            case 18:
            case 23:
            case 24:
            case 29:
            case 30:
                setScheduleDay(TimeTableType.SUNDAY);
                break;

            default:
                break;
        }
    }

    private void checkLineC()
    {
        if(pageIndex >= 13 && pageIndex <= 18)
        {
            lineC = true;
        }
        else
        {
            lineC = false;
        }
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public void addPageIndex()
    {
        this.pageIndex++;
        checkScheduleDayPage();
        checkLineC();
    }

    public TimeTableType getScheduleDay()
    {
        return scheduleDay;
    }

    private void setScheduleDay(TimeTableType scheduleDay)
    {
        this.scheduleDay = scheduleDay;
    }

    public boolean isLineC()
    {
        return lineC;
    }

    private void setLineC(boolean lineC)
    {
        this.lineC = lineC;
    }
}
