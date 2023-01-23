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

@CommandInfo(name = "mute", aliases = {"tempmute"})
public class MuteCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();

        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getPermanentMuteCommandPermission(), permissionsConfig.getTempMuteCommandPermission())) return;

        var extractedDetails = PunishmentsUtils.extractBasePunishmentDetails(sender, args, "mute");
        if(extractedDetails == null) return;

        var requiredPermission = extractedDetails.isPermanent() ? permissionsConfig.getPermanentMuteCommandPermission() : permissionsConfig.getTempMuteCommandPermission();
        if(!PunishmentsUtils.hasPermissions(sender, requiredPermission)) return;

        var target = Bukkit.getPlayer(extractedDetails.nickname());

        var punishment = new Punishment(new ObjectId(), extractedDetails.nickname(), extractedDetails.reason(), sender.getName(), System.currentTimeMillis(), (extractedDetails.isPermanent() ? -1L : System.currentTimeMillis() + extractedDetails.duration()), PunishmentType.MUTE, true, "-", "-");
        var dataStore = Managers.getManager(DatabaseManager.class).getDataStore();
        dataStore.insertPunishment(punishment);
        var manager = getManager(PunishmentsManager.class);
        manager.addPunishment(punishment);

        var messageConfig = getMessagesConfig();
        if(extractedDetails.silent()) {
            BukkitUtils.broadcast(messageConfig.getMuteCommandAlertSilent(target.getName(), extractedDetails.reason(), extractedDetails.issuer()), permissionsConfig.getSilentByPassPermission());
        } else {
            BukkitUtils.broadcast(messageConfig.getMuteCommandAlertPublic(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        }

        sender.sendMessage(messageConfig.getMuteCommandSuccess(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        target.sendMessage(messageConfig.getMuteCommandAlertTarget(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
    }

}
