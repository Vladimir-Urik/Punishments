package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.DatabaseManager;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.BukkitUtils;
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

        var punishment = new Punishment(new ObjectId(), extractedDetails.nickname(), extractedDetails.reason(), sender.getName(), System.currentTimeMillis(), -1L, PunishmentType.WARN, true, "-", "-");
        var dataStore = Managers.getManager(DatabaseManager.class).getDataStore();
        dataStore.updatePunishment(punishment);
        var manager = getManager(PunishmentsManager.class);
        manager.addPunishment(punishment);

        var messageConfig = getMessagesConfig();
        if(extractedDetails.silent()) {
            BukkitUtils.broadcast(messageConfig.getWarnCommandAlertSilent(extractedDetails.nickname(), extractedDetails.reason(), extractedDetails.issuer()), permissionsConfig.getSilentByPassPermission());
        } else {
            BukkitUtils.broadcast(messageConfig.getWarnCommandAlertPublic(extractedDetails.nickname(), extractedDetails.reason(), extractedDetails.issuer()));
        }

        sender.sendMessage(messageConfig.getWarnCommandSuccess(extractedDetails.nickname(), extractedDetails.reason(), extractedDetails.issuer()));

        var target = Bukkit.getPlayer(extractedDetails.nickname());
        if(target == null) return;

        target.sendMessage(messageConfig.getWarnCommandAlertTarget(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
    }

}
