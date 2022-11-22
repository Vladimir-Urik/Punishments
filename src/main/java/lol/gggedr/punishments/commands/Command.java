package lol.gggedr.punishments.commands;

import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.configurations.impl.LayoutsConfig;
import lol.gggedr.punishments.configurations.impl.MessagesConfig;
import lol.gggedr.punishments.configurations.impl.PermissionsConfig;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

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

    default LayoutsConfig getLayoutsConfig() {
        return getConfigurationsManager().getConfig(LayoutsConfig.class);
    }

    default <T extends Manager> T getManager(Class<T> clazz) {
        return Managers.getManager(clazz);
    }

    default org.bukkit.command.Command toBukkitCommand() {
        var commandInfo = getClass().getAnnotation(CommandInfo.class);
        var name = commandInfo.name();
        var description = commandInfo.description();
        var aliases = Arrays.stream(commandInfo.aliases()).toList();

        return new org.bukkit.command.Command(name, description, "", aliases) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                Command.this.execute(sender, args);
                return true;
            }
        };
    }

}
