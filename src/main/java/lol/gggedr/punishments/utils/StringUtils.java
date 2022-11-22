package lol.gggedr.punishments.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class StringUtils {

    /**
     * It takes a string, and replaces all instances of '&' with the Minecraft color code character
     *
     * @param string The string you want to colorize.
     * @return The string with the color codes replaced with the actual color.
     */
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Given a list of strings, return a list of strings where each string is colorized.
     *
     * @param strings The list of strings to colorize.
     * @return A list of strings
     */
    public static List<String> colorize(List<String> strings) {
        return strings.stream().map(StringUtils::colorize).toList();
    }

    /**
     * It takes a list of strings, joins them together with a newline character, and then colorizes the result
     *
     * @param strings The list of strings to join together.
     * @return A string with the color code &r
     */
    public static String toString(List<String> strings) {
        return StringUtils.colorize(String.join("\n&r", strings));
    }

}
