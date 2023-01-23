package lol.gggedr.punishments.commands.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.managers.impl.DatabaseManager;
import lol.gggedr.punishments.menus.impl.HistoryMenu;
import lol.gggedr.punishments.utils.PunishmentsUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "history")
public class HistoryCommand implements Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(getMessagesConfig().getOnlyPlayersMessage());
            return;
        }

        var permissionsConfig = getPermissionsConfig();
        if(!PunishmentsUtils.hasPermissions(sender, permissionsConfig.getHistoryCommandPermission())) return;

        if(args.length != 1) {
            sender.sendMessage(getMessagesConfig().getCommandUsage("history"));
            return;
        }

        var target = args[0];
        var punishments = getManager(DatabaseManager.class).getDataStore().getAllPunishments(target);

        if(punishments.isEmpty()) {
            sender.sendMessage(getMessagesConfig().getHistoryCommandNoHistory(target));
            return;
        }

        var menu = new HistoryMenu(player, target, punishments);
        menu.openMenu();
    }

}
