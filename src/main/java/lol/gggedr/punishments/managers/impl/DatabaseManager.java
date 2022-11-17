package lol.gggedr.punishments.managers.impl;

import com.mongodb.*;
import lol.gggedr.punishments.configurations.impl.DatabaseConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;
import org.bukkit.Bukkit;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

public class DatabaseManager implements Manager {

    private MongoClient client;
    private DB database;
    private DBCollection collection;

    @Override
    public void onEnable() {
        var config = Managers.getManager(ConfigurationsManager.class).getConfig(DatabaseConfig.class);
        try {
            client = new MongoClient(new MongoClientURI(craftUrl(config)));
            database = client.getDB(config.getDatabase());
            collection = database.getCollection(config.getCollection());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private String craftUrl(DatabaseConfig config) {
        return "mongodb://" + config.getUser() + ":" + config.getPassword() + "@" + config.getHost() + ":" + config.getPort();
    }

    @Override
    public void onDisable() {
        client.close();
    }

    public DBCollection getCollection() {
        return collection;
    }

    public DB getDatabase() {
        return database;
    }

    public MongoClient getClient() {
        return client;
    }

    public Punishment getActiveBans(String playerName) {
        var query = new BasicDBObject("nickname", playerName).append("type", PunishmentType.BAN.name());
        return Punishment.fromDocument(collection.findOne(query));
    }

}
