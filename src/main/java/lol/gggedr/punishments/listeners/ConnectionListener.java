package lol.gggedr.punishments.listeners;

import lol.gggedr.punishments.configurations.impl.LayoutsConfig;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class ConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        var player = event.getName();

        var manager = Managers.getManager(PunishmentsManager.class);
        manager.loadPunishments(player);

        if (!manager.isPunished(player, PunishmentType.BAN)) return;

        var punishment = manager.getPunishment(player, PunishmentType.BAN).getValue();
        var reason = punishment.reason();
        var permanent = punishment.end() == -1;

        var config = Managers.getManager(ConfigurationsManager.class).getConfig(LayoutsConfig.class);
        var message = permanent ? config.getBanPermanent(reason, punishment.issuer()) : config.getBanTemp(reason, punishment.issuer(), punishment.end());

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
    }

}
