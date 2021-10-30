package com.jaoow.bungeeactivity.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Data
@NoArgsConstructor
public class OnlineRecord {

    public ZonedDateTime lastSeen = ZonedDateTime.now(); // timestamp millisecond
    public long dailyTime; // millisecond
    public long weeklyTime; // millisecond
    public long monthlyTime; // millisecond
    public long totalTime; // millisecond


    public void updateTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime lastSeen = this.lastSeen;

        long duration = Duration.between(lastSeen, currentTime).toMillis();

        ZonedDateTime startOfToday = currentTime.truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime startOfWeek = currentTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime startOfMonth = currentTime.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);

        if (startOfToday.isAfter(lastSeen)) {
            this.dailyTime = Duration.between(startOfToday, currentTime).toMillis();
        } else {
            this.dailyTime += duration;
        }

        if (startOfWeek.isAfter(lastSeen)) {
            this.weeklyTime = 0;
        } else {
            this.weeklyTime += duration;
        }

        if (startOfMonth.isAfter(lastSeen)) {
            this.monthlyTime = 0;
        } else {
            this.monthlyTime += duration;
        }

        this.lastSeen = currentTime;
        this.totalTime += Math.max(0, duration);
    }
}
