package com.metroporto.import_timetables;

import com.metroporto.metro.Schedule;

public interface ScheduleInterface extends Insert<Schedule>
{
    Schedule findSchedule(int scheduleID);
}
