package lol.gggedr.punishments.listeners;

import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.MenusManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenusListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        var manager = Managers.getManager(MenusManager.class);
        var player = (Player) event.getWhoClicked();

        var menu = manager.getMenu(player);
        if (menu == null) return;

        event.setCancelled(true);
        menu.runAction(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        var manager = Managers.getManager(MenusManager.class);
        manager.removeMenuWithoutClosing((Player) event.getPlayer());
    }

}
