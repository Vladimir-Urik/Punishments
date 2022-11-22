package lol.gggedr.punishments.utils;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)([smhdwMy])");

    public static boolean isStartWithTime(String string) {
        return TIME_PATTERN.matcher(string).find();
    }

    public static long parseTime(String string) {
        var time = 0;
        var matcher = TIME_PATTERN.matcher(string);
        while (matcher.find()) {
            var number = Integer.parseInt(matcher.group(1));
            var unit = matcher.group(2);
            switch (unit) {
                case "s" -> time += number * 1000;
                case "m" -> time += number * 1000 * 60;
                case "h" -> time += number * 1000 * 60 * 60;
                case "d" -> time += number * 1000 * 60 * 60 * 24;
                case "w" -> time += number * 1000 * 60 * 60 * 24 * 7;
                case "M" -> time += number * 1000 * 60 * 60 * 24 * 30;
                case "y" -> time += number * 1000 * 60 * 60 * 24 * 365;
            }
        }
        return time;
    }

    public static String formatExpiration(long millis) {
        var years = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 365;
        millis -= TimeUnit.MILLISECONDS.convert(years, TimeUnit.DAYS);
        var months = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 30;
        millis -= TimeUnit.MILLISECONDS.convert(months, TimeUnit.DAYS);
        var weeks = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS) / 7;
        millis -= TimeUnit.MILLISECONDS.convert(weeks, TimeUnit.DAYS);
        var days = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS);
        millis -= TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS);
        var hours = TimeUnit.HOURS.convert(millis, TimeUnit.MILLISECONDS);
        millis -= TimeUnit.MILLISECONDS.convert(hours, TimeUnit.HOURS);
        var minutes = TimeUnit.MINUTES.convert(millis, TimeUnit.MILLISECONDS);
        millis -= TimeUnit.MILLISECONDS.convert(minutes, TimeUnit.MINUTES);
        var seconds = TimeUnit.SECONDS.convert(millis, TimeUnit.MILLISECONDS);

        var builder = new StringBuilder();
        if (years > 0) builder.append(years).append("y ");
        if (months > 0) builder.append(months).append("M ");
        if (weeks > 0) builder.append(weeks).append("w ");
        if (days > 0) builder.append(days).append("d ");
        if (hours > 0) builder.append(hours).append("h ");
        if (minutes > 0) builder.append(minutes).append("m ");
        if (seconds > 0) builder.append(seconds).append("s ");

        return builder.toString().trim();
    }

}
