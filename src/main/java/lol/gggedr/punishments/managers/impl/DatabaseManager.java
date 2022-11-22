package lol.gggedr.punishments.managers.impl;

import com.mongodb.*;
import lol.gggedr.punishments.configurations.impl.DatabaseConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onDisable() {
        client.close();
    }

    /**
     * It takes a DatabaseConfig object and returns a String that represents a MongoDB connection URL
     *
     * @param config The configuration object that contains the database connection information.
     * @return A string that is the url to the database.
     */
    private String craftUrl(DatabaseConfig config) {
        return "mongodb://" + config.getUser() + ":" + config.getPassword() + "@" + config.getHost() + ":" + config.getPort();
    }

    /**
     * This function returns the collection object.
     *
     * @return The collection object.
     */
    public DBCollection getCollection() {
        return collection;
    }

    /**
     * This function returns the database.
     *
     * @return The database object.
     */
    public DB getDatabase() {
        return database;
    }

    /**
     * This function returns the client object.
     *
     * @return The MongoClient object.
     */
    public MongoClient getClient() {
        return client;
    }

    /**
     * Get all active punishments for a player.
     *
     * @param playerName The player's name
     * @return A list of punishments
     */
    public List<Punishment> getActivePunishments(String playerName) {
        var query = new BasicDBObject("nickname", playerName).append("active", true);

        return preProcessPunishmentsQuery(query);
    }

    /**
     * Get all punishments for a player.
     *
     * @param playerName The player's name
     * @return A list of punishments.
     */
    public List<Punishment> getAllPunishments(String playerName) {
        var query = new BasicDBObject("nickname", playerName);
        return preProcessPunishmentsQuery(query);
    }


    /**
     * Get all punishments that have an end time less than the current time and are active.
     *
     * @return A list of punishments that are active and have an end time less than the current time.
     */
    public List<Punishment> getPunishmentsToExpire() {
        var query = new BasicDBObject("end", new BasicDBObject("$lt", System.currentTimeMillis())).append("active", true);
        return preProcessPunishmentsQuery(query).stream().filter(punishment -> punishment.end() != -1L).toList();
    }

    /**
     * This function takes a query, executes it, and returns a list of punishments.
     *
     * @param query The query to use to find the punishments.
     * @return A list of punishments.
     */
    private List<Punishment> preProcessPunishmentsQuery(BasicDBObject query) {
        var punishments = new ArrayList<Punishment>();
        try (var result = collection.find(query)) {
            while (result.hasNext()) {
                var punishment = result.next();
                punishments.add(Punishment.fromDocument(punishment));
            }
        }

        return punishments;
    }

}
