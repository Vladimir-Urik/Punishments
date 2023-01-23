package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.DatabaseManager;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "unban", aliases = {"pardon"})
public class UnbanCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();
        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getUnbanCommandPermission())) return;

        if(args.length < 1) {
            sender.sendMessage(getMessagesConfig().getCommandUsage("unban"));
            return;
        }

        var target = args[0];
        var punishmentsManager = getManager(PunishmentsManager.class);
        var punishment = punishmentsManager.getPlayerPunishment(target, PunishmentType.BAN);

        if(punishment == null) {
            sender.sendMessage(getMessagesConfig().getPlayerNotBanned(target));
            return;
        }

        var reason = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        var finalReason = reason.length() == 0 ? "-" : reason.toString();
        punishment.setActive(false);
        punishment.setRemovedBy(sender.getName());
        punishment.setRemoveReason(finalReason);
        var dataStore = Managers.getManager(DatabaseManager.class).getDataStore();
        dataStore.updatePunishment(punishment);

        punishmentsManager.removePunishment(target, PunishmentType.BAN);

        sender.sendMessage(getMessagesConfig().getUnbanCommandSuccess(target, finalReason));
    }

}
