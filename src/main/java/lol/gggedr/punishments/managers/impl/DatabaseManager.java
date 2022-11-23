package lol.gggedr.punishments.managers.impl;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lol.gggedr.punishments.configurations.impl.DatabaseConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Manager {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public void onEnable() {
        var config = Managers.getManager(ConfigurationsManager.class).getConfig(DatabaseConfig.class);
        client = new MongoClient(new MongoClientURI(craftUrl(config)));
        database = client.getDatabase(config.getDatabase());
        collection = database.getCollection(config.getCollection());
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
    public MongoCollection<Document> getCollection() {
        return collection;
    }

    /**
     * This function returns the database.
     *
     * @return The database object.
     */
    public MongoDatabase getDatabase() {
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

        var result = collection.find(query);
        for (var document : result) {
            punishments.add(Punishment.fromDocument(document));
        }

        return punishments;
    }

}
