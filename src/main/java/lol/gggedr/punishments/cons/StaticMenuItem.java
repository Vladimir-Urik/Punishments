package lol.gggedr.punishments.cons;

import lol.gggedr.punishments.menus.MenuAction;
import org.bukkit.inventory.ItemStack;

public class StaticMenuItem {

    private final int slot;
    private final MenuAction action;
    private final ItemStack itemStack;

    public StaticMenuItem(int slot, MenuAction action, ItemStack itemStack) {
        this.slot = slot;
        this.action = action;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public MenuAction getAction() {
        return action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
