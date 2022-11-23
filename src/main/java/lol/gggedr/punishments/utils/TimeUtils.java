package lol.gggedr.punishments.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)([a-zA-Z]+)");

    public static boolean isStartWithTime(String string) {
        return TIME_PATTERN.matcher(string).find();
    }

    public static long parseTime(String string) {
        var time = 0L;
        var matcher = TIME_PATTERN.matcher(string);
        while (matcher.find()) {
            var number = Integer.parseInt(matcher.group(1));
            var unit = matcher.group(2);
            switch (unit) {
                case "y" -> time += TimeUnit.DAYS.toMillis(number * 365L);
                case "mo" -> time += TimeUnit.DAYS.toMillis(number * 30L);
                case "w" -> time += TimeUnit.DAYS.toMillis(number * 7L);
                case "d" -> time += TimeUnit.DAYS.toMillis(number);
                case "h" -> time += TimeUnit.HOURS.toMillis(number);
                case "m" -> time += TimeUnit.MINUTES.toMillis(number);
                case "s" -> time += TimeUnit.SECONDS.toMillis(number);
            }
        }
        return time;
    }

    public static String formatExpiration(long millis) {
        var time = millis;
        var builder = new StringBuilder();
        var years = TimeUnit.MILLISECONDS.toDays(time) / 365L;
        if (years > 0) {
            builder.append(years).append("y ");
            time -= TimeUnit.DAYS.toMillis(years * 365L);
        }

        var months = TimeUnit.MILLISECONDS.toDays(time) / 30L;
        if (months > 0) {
            builder.append(months).append("mo ");
            time -= TimeUnit.DAYS.toMillis(months * 30L);
        }

        var days = TimeUnit.MILLISECONDS.toDays(time);
        if (days > 0) {
            builder.append(days).append("d ");
            time -= TimeUnit.DAYS.toMillis(days);
        }

        var hours = TimeUnit.MILLISECONDS.toHours(time);
        if (hours > 0) {
            builder.append(hours).append("h ");
            time -= TimeUnit.HOURS.toMillis(hours);
        }

        var minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if (minutes > 0) {
            builder.append(minutes).append("m ");
            time -= TimeUnit.MINUTES.toMillis(minutes);
        }

        var seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        if (seconds > 0) {
            builder.append(seconds).append("s ");
        }

        if(builder.length() == 0) {
            builder.append("0s");
        }

        return builder.toString();
    }

    public static String formatDate(long time) {
        var date = new Date(time);
        var format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return format.format(date);
    }

}
