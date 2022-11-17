package lol.gggedr.punishments.utils;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
