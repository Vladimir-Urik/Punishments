package lol.gggedr.punishments.utils;

import org.bukkit.Bukkit;

public class BukkitUtils {

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static void broadcast(String message, String permission) {
        for(var player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission(permission)) player.sendMessage(message);
        }
    }

}
