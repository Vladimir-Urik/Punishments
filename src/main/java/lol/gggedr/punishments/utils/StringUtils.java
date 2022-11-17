package lol.gggedr.punishments.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class StringUtils {

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> colorize(List<String> strings) {
        return strings.stream().map(StringUtils::colorize).toList();
    }

    public static String toString(List<String> strings) {
        return StringUtils.colorize(String.join("\n&r", strings));
    }

}
