package lol.gggedr.punishments.datastore;

import com.mongodb.BasicDBObject;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;

import java.util.List;

public interface DataStore {

    void connect();

    void disconnect();

    List<Punishment> getActivePunishments(String playerName);

    List<Punishment> getAllPunishments(String playerName);

    List<Punishment> getPunishmentsToExpire();

    Punishment getPunishment(String nickname, PunishmentType type);

    void insertPunishment(Punishment punishment);

    void updatePunishment(Punishment punishment);

    void deletePunishment(Punishment punishment);
}