package lol.gggedr.punishments.managers.impl;

import lol.gggedr.punishments.PunishmentsPlugin;
import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.managers.Manager;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationsManager implements Manager {

    private final List<Config> configurations = new ArrayList<>();

    @Override
    public void onEnable() {
        getPlugin().getDataFolder().mkdirs();

        var packagePath = PunishmentsPlugin.getInstance().getClass().getPackage().getName() + ".configurations.impl";
        var reflections = new Reflections(packagePath, Scanners.SubTypes);
        var subTypes = reflections.getSubTypesOf(Config.class);

        subTypes.forEach(configClass -> {
            try {
                var config = configClass.newInstance();
                config.loadConfiguration();
                configurations.add(config);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public <T extends Config> T getConfig(Class<T> configClass) {
        return (T) configurations.stream().filter(config -> config.getClass().equals(configClass)).findFirst().orElse(null);
    }

    public <T extends Config> T getConfig(String fileName) {
        return (T) configurations.stream().filter(config -> config.getClass().getAnnotation(ConfigInfo.class).fileName().equals(fileName)).findFirst().orElse(null);
    }

}
