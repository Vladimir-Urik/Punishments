package lol.gggedr.punishments.utils;

import lol.gggedr.punishments.configurations.impl.MessagesConfig;
import lol.gggedr.punishments.cons.BasePunishmentDetails;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class PunishmentsUtils {

    /**
     * It extracts the punishment details from the command arguments
     *
     * @param sender The CommandSender who executed the command.
     * @param args The arguments that the player has entered.
     * @param command The command that was used to execute the command.
     * @return A BasePunishmentDetails object.
     */
    public static BasePunishmentDetails extractBasePunishmentDetails(CommandSender sender, String[] args, String command) {
        var messagesConfig = Managers.getManager(ConfigurationsManager.class).getConfig(MessagesConfig.class);

        if(args.length < 1) {
            var usage = messagesConfig.getCommandUsage(command);
            sender.sendMessage(usage);
            return null;
        }

        var nickname = args[0];
        if(Bukkit.getPlayer(nickname) == null) {
            var playerNotFound = messagesConfig.getPlayerNotFoundMessage();
            sender.sendMessage(playerNotFound);
            return null;
        }

        var duration = -1L;
        var reason = "-";
        var silent = false;

        if(args.length == 1) {
            return new BasePunishmentDetails(nickname, reason, sender.getName(), duration, silent);
        }

        for(int i = 1; i < args.length; i++) {
            var arg = args[i];
            if(arg.startsWith("-")) {
                if(arg.equalsIgnoreCase("-s")) {
                    silent = true;
                }
                continue;
            }
            if((!command.equalsIgnoreCase("warn") && !command.equalsIgnoreCase("kick")) && TimeUtils.isStartWithTime(arg)) {
                duration = TimeUtils.parseTime(arg);
                continue;
            }
            reason = arg;
        }

        return new BasePunishmentDetails(nickname, reason, sender.getName(), duration, silent);
    }

    /**
     * If the sender has any of the permissions, return true, otherwise send the no permission message and return false
     *
     * @param sender The CommandSender that is executing the command.
     * @return A boolean
     */
    public static boolean hasPermissions(CommandSender sender, String... permissions) {
        var messagesConfig = Managers.getManager(ConfigurationsManager.class).getConfig(MessagesConfig.class);

        if(permissions.length == 0) {
            return true;
        }

        if(permissions.length > 1) {
            for(var permission : permissions) {
                if(sender.hasPermission(permission)) {
                    return true;
                }
            }

            sender.sendMessage(messagesConfig.getNoPermissionMessage());
            return false;
        }

        var permission = permissions[0];
        if(!sender.hasPermission(permission)) {
            sender.sendMessage(messagesConfig.getNoPermissionMessage());
            return false;
        }

        return true;
    }

}
