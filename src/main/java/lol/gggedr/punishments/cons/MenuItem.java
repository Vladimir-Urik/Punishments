package lol.gggedr.punishments.cons;

import lol.gggedr.punishments.menus.MenuAction;
import org.bukkit.inventory.ItemStack;

public class MenuItem {
    private final MenuAction action;
    private final ItemStack itemStack;

    public MenuItem(MenuAction action, ItemStack itemStack) {
        this.action = action;
        this.itemStack = itemStack;
    }

    public MenuAction getAction() {
        return action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
