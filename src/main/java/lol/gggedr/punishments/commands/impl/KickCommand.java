package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "kick")
public class KickCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var permissionsConfig = getPermissionsConfig();

        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getKickCommandPermission())) return;

        var extractedDetails = PunishmentsUtils.extractBasePunishmentDetails(sender, args, "kick");
        if(extractedDetails == null) return;

        // TODO: kick player
    }

}
