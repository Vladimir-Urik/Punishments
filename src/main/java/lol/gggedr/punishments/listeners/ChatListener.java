package lol.gggedr.punishments.listeners;

import lol.gggedr.punishments.configurations.impl.LayoutsConfig;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import lol.gggedr.punishments.managers.impl.PunishmentsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();

        var manager = Managers.getManager(PunishmentsManager.class);
        if(!manager.isPunished(player.getName(), PunishmentType.MUTE)) return;

        event.setCancelled(true);

        var punishment = manager.getPunishment(player.getName(), PunishmentType.MUTE).getValue();
        var reason = punishment.getReason();
        var permanent = punishment.getEnd() == -1;

        var config = Managers.getManager(ConfigurationsManager.class).getConfig(LayoutsConfig.class);
        var message = permanent ? config.getMutePermanent(reason, punishment.getIssuer()) : config.getMuteTemp(reason, punishment.getIssuer(), punishment.getEnd());

        player.sendMessage(message);
    }

}
