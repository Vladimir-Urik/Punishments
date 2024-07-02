package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.configurations.impl.MessagesConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import lol.gggedr.punishments.managers.impl.DatabaseManager;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.BukkitUtils;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "kick")
public class KickCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();
        var messagesConfig = getMessagesConfig();

        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getKickCommandPermission())) return;

        var extractedDetails = PunishmentsUtils.extractBasePunishmentDetails(sender, args, "kick");
        if(extractedDetails == null) return;

        var target = Bukkit.getPlayer(extractedDetails.nickname());
        if(target == null) {
            var playerNotFound = messagesConfig.getPlayerNotFoundMessage();
            sender.sendMessage(playerNotFound);
            return;
        }

        var punishment = new Punishment(new ObjectId(), extractedDetails.nickname(), extractedDetails.reason(), sender.getName(), System.currentTimeMillis(), -1L, PunishmentType.KICK, true, "-", "-");
        var dataStore = Managers.getManager(DatabaseManager.class).getDataStore();
        dataStore.insertPunishment(punishment);
        var manager = getManager(PunishmentsManager.class);
        manager.addPunishment(punishment);

        var messageConfig = getMessagesConfig();
        if(extractedDetails.silent()) {
            BukkitUtils.broadcast(messageConfig.getKickCommandAlertSilent(target.getName(), extractedDetails.reason(), extractedDetails.issuer()), permissionsConfig.getSilentByPassPermission());
        } else {
            BukkitUtils.broadcast(messageConfig.getKickCommandAlertPublic(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        }

        sender.sendMessage(messageConfig.getKickCommandSuccess(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));

        var layoutsConfig = getLayoutsConfig();
        target.kickPlayer(layoutsConfig.getKick(extractedDetails.reason(), extractedDetails.issuer()));
    }

}
