package lol.gggedr.punishments.menus;

import lol.gggedr.punishments.cons.MenuItem;
import lol.gggedr.punishments.cons.Pagination;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    private List<MenuItem> items = new ArrayList<>();
    private Pagination pagination = null;

    /**
     * GetTitle returns a String
     *
     * @return The title of the menu.
     */
    abstract String getTitle();

    /**
     * GetSize returns an int
     *
     * @return The size of the menu.
     */
    abstract int getSize();

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
}
