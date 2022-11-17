package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.utils.PunishmentsUtils;
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

        // TODO: mute player
    }

}
