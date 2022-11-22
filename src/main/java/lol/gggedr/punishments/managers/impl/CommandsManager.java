package lol.gggedr.punishments.managers.impl;

import lol.gggedr.punishments.commands.Command;
import lol.gggedr.punishments.commands.annotations.CommandInfo;
import lol.gggedr.punishments.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class CommandsManager implements Manager {

    @Override
    public void onEnable() {
        var reflection = new Reflections("lol.gggedr.punishments.commands.impl", Scanners.SubTypes)
                .getSubTypesOf(Command.class);

        reflection.forEach(command -> {
            var commandInfo = command.getAnnotation(CommandInfo.class);
            var commandName = commandInfo.name();
            var commandAliases = commandInfo.aliases();

            try {
                var instance = command.newInstance();
                var commandMap = getCommandMap();

                commandMap.register(getPlugin().getName().toLowerCase(), instance.toBukkitCommand());
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private CommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
        var bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);
        return (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
    }

}
