package lol.gggedr.punishments.managers.impl;

import lol.gggedr.punishments.cons.CachedValue;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PunishmentsManager implements Manager {

    private BukkitTask task;
    private List<CachedValue<Punishment>> punishments = new ArrayList<>();

    @Override
    public void onEnable() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), () -> {
            var dataStore = getManager(DatabaseManager.class).getDataStore();
            var punishmentsToExpire = dataStore.getPunishmentsToExpire();
            punishmentsToExpire.forEach(punishment -> {
                punishment.setActive(false);
                punishment.setRemovedBy("System");
                punishment.setRemoveReason("The punishment has expired.");

                dataStore.deletePunishment(punishment);
                punishments.removeIf(cachedValue -> cachedValue.getValue().getId().equals(punishment.getId()));
            });

            punishments.forEach((punishment) -> {
                var value = punishment.getValue();
                var player = Bukkit.getPlayer(value.getNickname());

                if (player == null) return;
                punishment.setTime(System.currentTimeMillis());
            });

            punishments.removeIf(punishmentCachedValue -> {
                return punishmentCachedValue.isExpired() || !punishmentCachedValue.getValue().isActive();
            });
        }, 0, 20);
    }

    @Override
    public void onDisable() {
        task.cancel();
    }

    /**
     * Add a punishment to the cache, and remove it after 15 minutes.
     *
     * @param punishment The punishment object to add to the cache.
     */
    public void addPunishment(Punishment punishment) {
        punishments = punishments.stream().filter(cachedValue -> {
            var value = cachedValue.getValue();
            return !value.getNickname().equals(punishment.getNickname()) && !value.getType().equals(punishment.getType());
        }).collect(Collectors.toList());

        punishments.add(new CachedValue<>(punishment, System.currentTimeMillis(), TimeUnit.MINUTES.toMillis(15)));
    }

    /**
     * It returns a list of cached values of punishments
     *
     * @return A list of cached values of punishments.
     */
    public List<CachedValue<Punishment>> getPunishments() {
        return punishments;
    }

    /**
     * Remove all punishments from the list that have the same nickname and type as the parameters
     *
     * @param nickname The nickname of the player you want to remove the punishment from.
     * @param type The type of punishment.
     */
    public void removePunishment(String nickname, PunishmentType type) {
        punishments.removeIf(punishment -> punishment.getValue().getNickname().equals(nickname) && punishment.getValue().getType() == type);
    }


    /**
     * Returns true if the player has a punishment of the specified type.
     *
     * @param nickname The nickname of the player you want to check.
     * @param type The type of punishment you want to check for.
     * @return A boolean value.
     */
    public boolean isPunished(String nickname, PunishmentType type) {
        return punishments.stream().anyMatch(punishment -> punishment.getValue().getNickname().equals(nickname) && punishment.getValue().getType() == type);
    }

    /**
     * Return the first punishment in the punishments list that has the same nickname and type as the given nickname and
     * type, or null if there is no such punishment.
     *
     * @param nickname The nickname of the player you want to get the punishment of.
     * @param type The type of punishment you want to get.
     * @return A cached value of a punishment.
     */
    public CachedValue<Punishment> getPunishment(String nickname, PunishmentType type) {
        return punishments.stream()
                .filter(punishment -> punishment.getValue().getNickname().equals(nickname) && punishment.getValue().getType() == type).findFirst()
                .orElse(null);
    }

    /**
     * If the punishments list doesn't contain a punishment with the same nickname as the one passed in, then get the
     * punishments from the database and add them to the list
     *
     * @param nickname The nickname of the player to load punishments for.
     */
    public void loadPunishments(String nickname) {
        if(punishments.stream().anyMatch(punishment -> punishment.getValue().getNickname().equals(nickname))) {
            return;
        }

        var dataStore = getManager(DatabaseManager.class).getDataStore();
        var punishments = dataStore.getActivePunishments(nickname);
        punishments.forEach(punishment -> this.punishments.add(new CachedValue<>(punishment, System.currentTimeMillis(), TimeUnit.MINUTES.toMillis(15))));
    }

    public Punishment getPlayerPunishment(String nickname, PunishmentType type) {
        var punishment = getPunishment(nickname, type);
        if(punishment != null) return punishment.getValue();

        var dataStore = getManager(DatabaseManager.class).getDataStore();
        return dataStore.getPunishment(nickname, type);
    }

}
