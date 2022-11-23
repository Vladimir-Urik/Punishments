package lol.gggedr.punishments.menus.impl;

import lol.gggedr.punishments.configurations.impl.HistoryMenuConfig;
import lol.gggedr.punishments.cons.MenuItem;
import lol.gggedr.punishments.cons.Pagination;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.cons.StaticMenuItem;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import lol.gggedr.punishments.menus.Menu;
import lol.gggedr.punishments.utils.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HistoryMenu extends Menu {

    private final String target;

    public HistoryMenu(Player player, String target, List<Punishment> punishments) {
        super(player);
        this.target = target;

        var config = getManager(ConfigurationsManager.class).getConfig(HistoryMenuConfig.class);

        var pagination = new Pagination(9, 44);
        setPagination(pagination);

        var staticItems = getStaticItems();
        var emptyGlass = getEmptyGlass();

        for (int i = 0; i < pagination.from(); i++) {
            staticItems.add(new StaticMenuItem(i, (event) -> {}, emptyGlass));
        }

        for (int i = (pagination.to() +1); i < getSize(); i++) {
            if(i == 47) continue;
            if(i == 51) continue;

            staticItems.add(new StaticMenuItem(i, (event) -> {}, emptyGlass));
        }

        staticItems.add(new StaticMenuItem(47, (event) -> {
            this.previousPage();
        }, config.getPreviousItem()));

        staticItems.add(new StaticMenuItem(51, (event) -> {
            this.nextPage();
        }, config.getNextItem()));

        for (var punishment: punishments) {
            var formattedStart = TimeUtils.formatDate(punishment.getStart());
            var duration = punishment.getEnd() == -1 ? "-" : TimeUtils.formatExpiration(punishment.getEnd() - punishment.getStart());
            var item = config.getInfoItem(punishment.getType().name(), punishment.getReason(), punishment.getIssuer(), formattedStart, duration, punishment.isActive(), punishment.getId().toString(), punishment.getRemovedBy(), punishment.getRemoveReason());

            this.addItem(new MenuItem((event) -> {}, item));
        }
    }

    public String getTitle() {
        var config = getManager(ConfigurationsManager.class).getConfig(HistoryMenuConfig.class);
        return config.getTitle(target);
    }

    public int getSize() {
        return 54;
    }

    private ItemStack getEmptyGlass() {
        var item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        var meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName("§f ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
