package lol.gggedr.punishments.configurations;

import lol.gggedr.punishments.PunishmentsPlugin;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

public interface Config {

    default YamlConfiguration generateDefaultConfiguration() {
        var yaml = new YamlConfiguration();

        var fields = Arrays.stream(this.getClass().getFields())
                .filter((field) -> field.isAnnotationPresent(ConfigField.class)).toList();

        fields.forEach((field) -> {
            try {
                var annotation = field.getAnnotation(ConfigField.class);

                var path = annotation.path();
                var defaultValue = field.get(this);
                var comments = annotation.comments();

                if (defaultValue == null) return;
                yaml.set(path, defaultValue);

                if (comments.length == 0) return;
                yaml.setComments(path, Arrays.stream(comments).toList());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return yaml;
    }

    default void loadConfiguration() {
        var configInfo = this.getClass().getAnnotation(ConfigInfo.class);

        var file = new File(PunishmentsPlugin.getInstance().getDataFolder(), configInfo.fileName());
        if(!file.exists()) {
            var yaml = generateDefaultConfiguration();
            yaml.setComments("", Arrays.stream(configInfo.headerComments()).toList());
            try {
                yaml.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        var yaml = YamlConfiguration.loadConfiguration(file);
        var fields = Arrays.stream(this.getClass().getFields())
                .filter((field) -> field.isAnnotationPresent(ConfigField.class)).toList();

        fields.forEach((field) -> {
            try {
                var annotation = field.getAnnotation(ConfigField.class);

                var path = annotation.path();
                var defaultValue = field.get(this);

                if(!yaml.contains(path)) {
                    yaml.set(path, defaultValue);
                }

                field.set(this, yaml.get(path));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}
