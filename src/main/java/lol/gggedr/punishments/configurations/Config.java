package lol.gggedr.punishments.configurations;

import lol.gggedr.punishments.PunishmentsPlugin;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

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
        var fields = Arrays.stream(this.getClass().getDeclaredFields())
                .filter((field) -> field.isAnnotationPresent(ConfigField.class)).toList();

        AtomicBoolean isUpdated = new AtomicBoolean(false);
        fields.forEach((field) -> {
            try {
                var annotation = field.getAnnotation(ConfigField.class);

                field.setAccessible(true);
                var path = annotation.path();
                var defaultValue = field.get(this);

                if(!yaml.contains(path)) {
                    if(defaultValue.getClass().isAssignableFrom(Material.class)) {
                        yaml.set(path, defaultValue.toString());
                    } else {
                        yaml.set(path, defaultValue);
                    }

                    isUpdated.set(true);
                }

                if(field.getType().isAssignableFrom(Material.class)) {
                    var material = Material.matchMaterial(yaml.getString(path));
                    field.set(this, material);
                    return;
                }

                field.set(this, yaml.get(path));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        if(isUpdated.get()) {
            try {
                yaml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
