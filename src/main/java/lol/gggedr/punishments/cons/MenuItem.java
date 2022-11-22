package lol.gggedr.punishments.cons;

import org.bukkit.inventory.ItemStack;

public class MenuItem {

    private final int slot;
    private final MenuAction action;
    private final ItemStack itemStack;

    public MenuItem(int slot, MenuAction action, ItemStack itemStack) {
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

    interface MenuAction {

        void execute();

    }

}
