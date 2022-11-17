package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "warn")
public class WarnCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();

        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getWarnCommandPermission())) return;

        var extractedDetails = PunishmentsUtils.extractBasePunishmentDetails(sender, args, "warn");
        if(extractedDetails == null) return;

        var target = Bukkit.getPlayer(extractedDetails.nickname());
        // TODO: Write to database

        var messageConfig = getMessagesConfig();
        if(extractedDetails.silent()) {
            Bukkit.broadcast(messageConfig.getWarnCommandAlertSilent(target.getName(), extractedDetails.reason(), extractedDetails.issuer()), permissionsConfig.getSilentByPassPermission());
        } else {
            Bukkit.broadcastMessage(messageConfig.getWarnCommandAlertPublic(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        }

        sender.sendMessage(messageConfig.getWarnCommandSuccess(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        target.sendMessage(messageConfig.getWarnCommandAlertTarget(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
    }

}
