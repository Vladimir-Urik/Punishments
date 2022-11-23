package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bson.types.ObjectId;
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

        var punishment = new Punishment(new ObjectId(), extractedDetails.nickname(), extractedDetails.reason(), sender.getName(), System.currentTimeMillis(), -1L, PunishmentType.WARN, true, "-", "-");
        punishment.insert();
        var manager = getManager(PunishmentsManager.class);
        manager.addPunishment(punishment);

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
