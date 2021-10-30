package com.jaoow.bungeeactivity.utils;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class Utils {

    public static String color(String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    public static @NotNull String getFormattedTime(long val) {
        Duration duration = Duration.ofMillis(val);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = (duration.toMillis() / 1000) % 60;
        String format = "";
        if (days > 0) {
            format += days + "d ";
        }
        if (hours > 0) {
            format += hours + "h ";
        }
        if (minutes > 0) {
            format += minutes + "m ";
        }
        if (seconds > 0) {
            format += seconds + "s ";
        }
        if (format.equals("")) {
            return "0s";
        } else {
            return format.trim();
        }
    }
}
