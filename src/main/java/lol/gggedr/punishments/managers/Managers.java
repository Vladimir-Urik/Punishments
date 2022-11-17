package lol.gggedr.punishments.managers;

import java.util.ArrayList;
import java.util.List;

public class Managers {

    private static final List<Manager> managers = new ArrayList<>();

    public static void register(Class<? extends Manager> clazz) {
        try {
            managers.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void onEnable() {
        managers.forEach(Manager::onEnable);
    }

    public static void onDisable() {
        managers.forEach(Manager::onDisable);
    }

    public static <T extends Manager> T getManager(Class<T> clazz) {
        return managers.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

}
