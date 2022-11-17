package lol.gggedr.punishments;

import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PunishmentsPlugin extends JavaPlugin {

    private static PunishmentsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        Managers.register(ConfigurationsManager.class);

        Managers.onEnable();
    }

    @Override
    public void onDisable() {
        Managers.onDisable();
    }

    public static PunishmentsPlugin getInstance() {
        return instance;
    }
}
