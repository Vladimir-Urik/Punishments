package lol.gggedr.punishments.managers.impl;

import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.menus.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MenusManager implements Manager {

    private final HashMap<String, Menu> openedMenus = new HashMap<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        for (Menu menu : openedMenus.values()) {
            menu.closeMenu();
        }

        openedMenus.clear();
    }


    public void openMenu(Player player, Menu menu) {
        closeMenu(player);
        openedMenus.put(player.getName(), menu);
    }

    public void closeMenu(Player player) {
        if (openedMenus.containsKey(player.getName())) {
            openedMenus.get(player.getName()).closeMenu();
            openedMenus.remove(player.getName());
        }
    }

    public void removeMenuWithoutClosing(Player player) {
        openedMenus.remove(player.getName());
    }

    public Menu getMenu(Player player) {
        return openedMenus.get(player.getName());
    }

}
