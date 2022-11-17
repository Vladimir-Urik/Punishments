package lol.gggedr.punishments.utils;

import lol.gggedr.punishments.configurations.impl.MessagesConfig;
import lol.gggedr.punishments.cons.BasePunishmentDetails;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bukkit.command.CommandSender;

public class PunishmentsUtils {

    public static BasePunishmentDetails extractBasePunishmentDetails(CommandSender sender, String[] args, String command) {
        var messagesConfig = Managers.getManager(ConfigurationsManager.class).getConfig(MessagesConfig.class);

        if(args.length < 1) {
            var usage = messagesConfig.getCommandUsage(command);
            sender.sendMessage(usage);
            return null;
        }

        var nickname = args[0];

        var duration = 0L;
        var reason = "-";
        var silent = false;

        if(args.length == 1) {
            return new BasePunishmentDetails(nickname, reason, sender.getName(), duration);
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

        return new BasePunishmentDetails(nickname, reason, sender.getName(), duration);
    }

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
