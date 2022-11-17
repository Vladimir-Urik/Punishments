package lol.gggedr.punishments.utils;

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

}
