package lol.gggedr.punishments.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;
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
     * It replaces all placeholders in a string with their corresponding values
     *
     * @param string The string to replace the placeholders in.
     * @param placeholders A HashMap of placeholders and their values.
     * @return A string with the placeholders replaced with the values in the HashMap.
     */
    public static String replacePlaceholders(String string, HashMap<String, Object> placeholders) {
        for(var entry : placeholders.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue().toString());
        }

        return string;
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
     * "Replace all placeholders in a list of strings with their corresponding values."
     * <p>
     * The function takes two arguments:
     * <p>
     * * `strings`: A list of strings.
     * * `placeholders`: A map of placeholder names to their corresponding values
     *
     * @param strings The list of strings to replace placeholders in.
     * @param placeholders A HashMap of placeholders to replace. The key is the placeholder, and the value is the
     * replacement.
     * @return A list of strings with the placeholders replaced with the values from the hashmap.
     */
    public static List<String> replacePlaceholders(List<String> strings, HashMap<String, Object> placeholders) {
        if(placeholders == null || placeholders.isEmpty()) return strings;

        return strings.stream().map(string -> replacePlaceholders(string, placeholders)).toList();
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
