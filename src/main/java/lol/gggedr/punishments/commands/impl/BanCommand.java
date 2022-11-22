package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "ban", aliases = {"tempban"})
public class BanCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();

        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getPermanentBanCommandPermission(), permissionsConfig.getTempBanCommandPermission())) return;

        var extractedDetails = PunishmentsUtils.extractBasePunishmentDetails(sender, args, "ban");
        if(extractedDetails == null) return;

        var requiredPermission = extractedDetails.isPermanent() ? permissionsConfig.getPermanentBanCommandPermission() : permissionsConfig.getTempBanCommandPermission();
        if(!PunishmentsUtils.hasPermissions(sender, requiredPermission)) return;

        var target = Bukkit.getPlayer(extractedDetails.nickname());

        var punishment = new Punishment("", extractedDetails.nickname(), extractedDetails.reason(), sender.getName(), System.currentTimeMillis(), (extractedDetails.isPermanent() ? -1L : System.currentTimeMillis() + extractedDetails.duration()), PunishmentType.BAN, true, "-", "-");
        punishment.insert();
        var manager = getManager(PunishmentsManager.class);
        manager.addPunishment(punishment);

        var messageConfig = getMessagesConfig();
        if(extractedDetails.silent()) {
            Bukkit.broadcast(messageConfig.getKickCommandAlertSilent(target.getName(), extractedDetails.reason(), extractedDetails.issuer()), permissionsConfig.getSilentByPassPermission());
        } else {
            Bukkit.broadcastMessage(messageConfig.getKickCommandAlertPublic(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));
        }

        sender.sendMessage(messageConfig.getKickCommandSuccess(target.getName(), extractedDetails.reason(), extractedDetails.issuer()));

        var layoutsConfig = getLayoutsConfig();
        var layout = extractedDetails.isPermanent() ? layoutsConfig.getBanPermanent(extractedDetails.reason(), extractedDetails.issuer()) : layoutsConfig.getBanTemp(extractedDetails.reason(), extractedDetails.issuer(), (System.currentTimeMillis() + extractedDetails.duration()));
        target.kickPlayer(layout);
    }

}
