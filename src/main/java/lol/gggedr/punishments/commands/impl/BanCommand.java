package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.utils.PunishmentsUtils;
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

        // TODO: ban player
    }

}
