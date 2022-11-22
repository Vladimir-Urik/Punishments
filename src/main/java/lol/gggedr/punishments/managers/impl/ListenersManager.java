package lol.gggedr.punishments.managers.impl;

import lol.gggedr.punishments.managers.Manager;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class ListenersManager implements Manager {

    @Override
    public void onEnable() {
        var reflection = new Reflections("lol.gggedr.punishments.listeners", Scanners.SubTypes)
                .getSubTypesOf(Listener.class);

        reflection.forEach(listener -> {
            try {
                var instance = listener.newInstance();
                getPlugin().getServer().getPluginManager().registerEvents(instance, getPlugin());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
