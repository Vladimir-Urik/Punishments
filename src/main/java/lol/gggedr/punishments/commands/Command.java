package lol.gggedr.punishments.commands;

import lol.gggedr.punishments.configurations.impl.MessagesConfig;
import lol.gggedr.punishments.configurations.impl.PermissionsConfig;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bukkit.command.CommandSender;

public interface Command {

    void execute(CommandSender sender, String[] args);

    default ConfigurationsManager getConfigurationsManager() {
        return Managers.getManager(ConfigurationsManager.class);
    }

    default PermissionsConfig getPermissionsConfig() {
        return getConfigurationsManager().getConfig(PermissionsConfig.class);
    }

    default MessagesConfig getMessagesConfig() {
        return getConfigurationsManager().getConfig(MessagesConfig.class);
    }
}
