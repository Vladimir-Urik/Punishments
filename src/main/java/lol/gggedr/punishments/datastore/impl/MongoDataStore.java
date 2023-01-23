package lol.gggedr.punishments.datastore.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lol.gggedr.punishments.configurations.impl.DatabaseConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.datastore.DataStore;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.ConfigurationsManager;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDataStore implements DataStore {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public void connect() {
        var config = Managers.getManager(ConfigurationsManager.class).getConfig(DatabaseConfig.class);
        client = new MongoClient(new MongoClientURI(craftUrl(config)));
        database = client.getDatabase(config.getDatabase());
        collection = database.getCollection(config.getCollection());
    }

    @Override
    public void disconnect() {
        client.close();
    }

    private String craftUrl(DatabaseConfig config) {
        return "mongodb://" + config.getUser() + ":" + config.getPassword() + "@" + config.getHost() + ":" + config.getPort();
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
        return preProcessPunishmentsQuery(query).stream().filter(punishment -> punishment.getEnd() != -1L).toList();
    }

    public Punishment getPunishment(String nickname, PunishmentType type) {
        var query = new BasicDBObject("nickname", nickname).append("type", type.name()).append("active", true);
        return preProcessPunishmentsQuery(query).stream().findFirst().orElse(null);
    }

    @Override
    public void insertPunishment(Punishment punishment) {
        var query = new BasicDBObject();
        query.put("nickname", punishment.getNickname());
        query.put("type", punishment.getType().name());
        query.put("active", true);

        var updatedDocument = new Document();
        updatedDocument.put("active", false);
        updatedDocument.put("removedBy", punishment.getIssuer());
        updatedDocument.put("removedReason", "Replaced by a new punishment.");

        var document = new Document();
        document.put("$set", updatedDocument);
        collection.updateMany(query, document);

        collection.insertOne(punishment.toDocument());
    }

    @Override
    public void updatePunishment(Punishment punishment) {
        var query = new BasicDBObject("_id", punishment.getId());
        var update = new BasicDBObject("$set", punishment.toUpdateDocument());

        collection.findOneAndUpdate(query, update);
    }

    @Override
    public void deletePunishment(Punishment punishment) {
        collection.deleteOne(new BasicDBObject("_id", punishment.getId()));
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
