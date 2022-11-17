package lol.gggedr.punishments.managers;

import lol.gggedr.punishments.PunishmentsPlugin;

public interface Manager {

    default void onEnable() {

    }

    default void onDisable() {

    }

    default PunishmentsPlugin getPlugin() {
        return PunishmentsPlugin.getInstance();
    }

    default <T extends Manager> T getManager(Class<T> clazz) {
        return Managers.getManager(clazz);
    }

}
