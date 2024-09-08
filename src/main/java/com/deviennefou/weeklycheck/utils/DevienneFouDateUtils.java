package com.deviennefou.weeklycheck.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DevienneFouDateUtils {

    public static Date getWeekStart(){
        LocalDate currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        return Date.from(currentWeekStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
