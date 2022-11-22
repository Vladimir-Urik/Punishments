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

    /**
     * Return the first configuration object of the given type, or null if none exists.
     *
     * @param configClass The class of the configuration you want to get.
     * @return A configuration object of the specified type.
     */
    public <T extends Config> T getConfig(Class<T> configClass) {
        return (T) configurations.stream().filter(config -> config.getClass().equals(configClass)).findFirst().orElse(null);
    }

    /**
     * "Return the first configuration object whose file name matches the given file name, or null if no such object
     * exists."
     *
     * The first line of the function is a generic type declaration. This is a way of telling the compiler that the return
     * type of the function is a subclass of the Config class. This is necessary because the function returns a Config
     * object, but the actual object returned is a subclass of Config
     *
     * @param fileName The name of the file to load.
     * @return A Config object.
     */
    public <T extends Config> T getConfig(String fileName) {
        return (T) configurations.stream().filter(config -> config.getClass().getAnnotation(ConfigInfo.class).fileName().equals(fileName)).findFirst().orElse(null);
    }

}
