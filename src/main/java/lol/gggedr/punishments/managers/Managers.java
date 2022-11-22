package lol.gggedr.punishments.managers;

import java.util.ArrayList;
import java.util.List;

public class Managers {

    private static final List<Manager> managers = new ArrayList<>();


    public static void onEnable() {
        managers.forEach(Manager::onEnable);
    }

    public static void onDisable() {
        managers.forEach(Manager::onDisable);
    }

    /**
     * Return the first manager in the list that is an instance of the given class, or null if there is none.
     *
     * @param clazz The class of the manager you want to get.
     * @return A manager of the specified class.
     */
    public static <T extends Manager> T getManager(Class<T> clazz) {
        return managers.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new instance of the class passed in to the managers list.
     *
     * @param clazz The class of the manager you want to register.
     */
    public static void register(Class<? extends Manager> clazz) {
        try {
            managers.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
