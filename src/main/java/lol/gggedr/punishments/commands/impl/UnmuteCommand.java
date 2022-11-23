package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "unmute")
public class UnmuteCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();
        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getUnmuteCommandPermission())) return;

        if(args.length < 1) {
            sender.sendMessage(getMessagesConfig().getCommandUsage("unmute"));
            return;
        }

        var target = args[0];
        var punishmentsManager = getManager(PunishmentsManager.class);
        var punishment = punishmentsManager.getPlayerPunishment(target, PunishmentType.MUTE);

        if(punishment == null) {
            sender.sendMessage(getMessagesConfig().getPlayerNotMuted(target));
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
        punishment.update();

        punishmentsManager.removePunishment(target, PunishmentType.MUTE);

        sender.sendMessage(getMessagesConfig().getUnmuteCommandSuccess(target, finalReason));
    }

}
