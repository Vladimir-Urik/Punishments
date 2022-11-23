package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@ConfigInfo(fileName = "history-menu.yml")
public class HistoryMenuConfig implements Config {

    @ConfigField(path = "title")
    private String title = "&cHistory of &c&l%player%";

    @ConfigField(path = "items.info.name")
    private String infoItemName = "&c%type% &7- &c%reason%";

    @ConfigField(path = "items.info.lore")
    private List<String> infoItemLore = List.of(
            "&7Punished by &c%issuer%",
            "&7Date: &c%date%",
            "&7Duration: &c%duration%",
            "&7Active: &c%active%"
    );

    @ConfigField(path = "items.info.material")
    private Material infoItemMaterial = Material.PAPER;

    @ConfigField(path = "items.info.custom-model-data")
    private int infoItemCustomModelData = 0;

    @ConfigField(path = "items.pagination.next.name")
    private String nextItemName = "&cNext page";

    @ConfigField(path = "items.pagination.next.lore")
    private List<String> nextItemLore = List.of(
            "&7Click to go to the next page"
    );

    @ConfigField(path = "items.pagination.next.material")
    private Material nextItemMaterial = Material.ARROW;

    @ConfigField(path = "items.pagination.next.custom-model-data")
    private int nextItemCustomModelData = 0;

    @ConfigField(path = "items.pagination.previous.name")
    private String previousItemName = "&cPrevious page";

    @ConfigField(path = "items.pagination.previous.lore")
    private List<String> previousItemLore = List.of(
            "&7Click to go to the previous page"
    );

    @ConfigField(path = "items.pagination.previous.material")
    private Material previousItemMaterial = Material.ARROW;

    @ConfigField(path = "items.pagination.previous.custom-model-data")
    private int previousItemCustomModelData = 0;

    public String getTitle() {
        return title;
    }

    public ItemStack getInfoItem(String type, String reason, String issuer, String date, String duration, boolean active) {
        var placeholders = new HashMap<String, Object>();
        placeholders.put("type", type);
        placeholders.put("reason", reason);
        placeholders.put("issuer", issuer);
        placeholders.put("date", date);
        placeholders.put("duration", duration);
        placeholders.put("active", active ? "Yes" : "No");

        return buildItem(infoItemMaterial, infoItemName, infoItemLore, infoItemCustomModelData, placeholders);
    }

    public ItemStack getNextItem() {
        return buildItem(nextItemMaterial, nextItemName, nextItemLore, nextItemCustomModelData, null);
    }

    public ItemStack getPreviousItem() {
        return buildItem(previousItemMaterial, previousItemName, previousItemLore, previousItemCustomModelData, null);
    }

    private ItemStack buildItem(Material material, String name, List<String> lore, int customModelData, HashMap<String, Object> placeholders) {
        var item = new ItemStack(material);
        var meta = item.getItemMeta();
        assert meta != null : "Item meta is null.";

        meta.setDisplayName(StringUtils.colorize(name));
        meta.setLore(StringUtils.replacePlaceholders(StringUtils.colorize(lore), placeholders));
        meta.setCustomModelData(customModelData);

        item.setItemMeta(meta);
        return item;
    }
}
