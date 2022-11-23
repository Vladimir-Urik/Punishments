package lol.gggedr.punishments.menus;

import lol.gggedr.punishments.cons.MenuItem;
import lol.gggedr.punishments.cons.StaticMenuItem;
import lol.gggedr.punishments.cons.Pagination;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.MenusManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    private final Player player;
    private Inventory inventory;
    private List<StaticMenuItem> staticItems = new ArrayList<>();
    private List<MenuItem> items = new ArrayList<>();
    private Pagination pagination = null;

    public Menu(Player player) {
        this.player = player;
    }

    /**
     * GetTitle returns a String
     *
     * @return The title of the menu.
     */
    abstract protected String getTitle();

    /**
     * GetSize returns an int
     *
     * @return The size of the menu.
     */
    abstract protected int getSize();

    /**
     * This function returns a list of menu items.
     *
     * @return A list of MenuItem objects.
     */
    public List<StaticMenuItem> getStaticItems() {
        return staticItems;
    }

    /**
     * This function sets the items of the menu to the items passed in.
     *
     * @param staticItems The list of menu items to be displayed.
     */
    public void setStaticItems(List<StaticMenuItem> staticItems) {
        this.staticItems = staticItems;
    }

    /**
     * This function returns a list of menu items.
     *
     * @return A list of MenuItem objects.
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * This function sets the items of the menu to the items passed in.
     *
     * @param items The list of menu items to be displayed.
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * > This function returns the pagination object
     *
     * @return The pagination object.
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * > This function sets the pagination object
     *
     * @param pagination The pagination object that contains the current page number and the number of items per page.
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * Get the manager of the given class.
     *
     * @param clazz The class of the manager you want to get.
     * @return A manager of the specified type.
     */
    protected <T extends Manager> T getManager(Class<T> clazz) {
        return Managers.getManager(clazz);
    }

    /**
     * If the pagination is not null, and the current page is less than the maximum page, then increment the page and
     * render the inventory
     */
    public void nextPage() {
        if (pagination == null) return;
        if ((pagination.page()+1) > getMaxPage()) return;

        pagination.page(pagination.page()+1);
        renderInventory();
    }

    /**
     * If the pagination object exists, and the current page is greater than 1, then decrement the page number and render
     * the inventory
     */
    public void previousPage() {
        if (pagination == null) return;
        if ((pagination.page()-1) < 1) return;

        pagination.page(pagination.page()-1);
        renderInventory();
    }

    /**
     * "Open the menu for the player."
     *
     * The first thing we do is get the MenusManager class. This is the class that handles all of the menus
     */
    public void openMenu() {
        var manager = getManager(MenusManager.class);
        manager.closeMenu(player);

        this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
        renderInventory();

        manager.openMenu(player, this);
        player.openInventory(inventory);
    }

    public void closeMenu() {
        player.closeInventory();

        var manager = getManager(MenusManager.class);
        manager.closeMenu(player);
    }

    /**
     * This function returns the player.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * If the slot is a static item, run the action. If the slot is a paginated item, run the action
     *
     * @param event The InventoryClickEvent that was called.
     */
    public void runAction(InventoryClickEvent event) {
        var slot = event.getSlot();
        var page = pagination.page();

        if(slot < 0 || slot >= getSize()) return;

        var staticItem = staticItems.stream().filter(i -> i.getSlot() == slot).findFirst().orElse(null);
        if (staticItem != null) {
            staticItem.getAction().execute(event);
            return;
        }

        if(pagination == null) return;
        var itemIndex = (page * (pagination.to() - pagination.from())) + slot;

        if (itemIndex >= items.size()) return;
        var item = items.get(itemIndex);
        item.getAction().execute(event);
    }

    /**
     * "We're going to set the contents of the inventory to an empty array, then we're going to set the static items, then
     * we're going to set the paginated items."
     *
     * Let's break it down
     */
    private void renderInventory() {
        this.inventory.setContents(new ItemStack[getSize()]);
        for (StaticMenuItem item : staticItems) {
            inventory.setItem(item.getSlot(), item.getItemStack());
        }

        if(pagination == null) return;

        var perPage = pagination.to() - pagination.from();
        var firstItemIndex = (pagination.page() - 1) * perPage;
        var lastItemIndex = firstItemIndex + perPage;

        var slot = pagination.from();
        for (int i = firstItemIndex; i <= lastItemIndex; i++) {
            if(items.size() <= i) break;

            var item = items.get(i);
            inventory.setItem(slot, item.getItemStack());
            slot++;

            if(slot > pagination.to()) break;
        }

        var manager = getManager(MenusManager.class);
        var menu = manager.getMenu(player);
        if(menu != null) {
            player.updateInventory();
        }
    }

    /**
     * If pagination is null, return 1, otherwise return the number of pages needed to display all items.
     *
     * @return The maximum page number.
     */
    private int getMaxPage() {
        if(pagination == null) return 1;
        return (int) Math.ceil((double) items.size() / (pagination.to() - pagination.from()));
    }
}
