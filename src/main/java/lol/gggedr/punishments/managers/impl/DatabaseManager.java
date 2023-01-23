package lol.gggedr.punishments.managers.impl;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lol.gggedr.punishments.configurations.impl.DatabaseConfig;
import lol.gggedr.punishments.cons.Punishment;
import lol.gggedr.punishments.datastore.DataStore;
import lol.gggedr.punishments.datastore.impl.MongoDataStore;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Manager;
import lol.gggedr.punishments.managers.Managers;
import org.bson.Document;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Manager {

    private DataStore dataStore;

    @Override
    public void onEnable() {
        var config = Managers.getManager(ConfigurationsManager.class).getConfig(DatabaseConfig.class);
        switch (config.getType().toLowerCase()) {
            case "mongo":
                dataStore = new MongoDataStore();
                break;
            case "mysql", "mariadb", "postgresql":
                // dataStore = new SQLDataStore();
                break;
            default:
                throw new IllegalArgumentException("Invalid database type: " + config.getType() + "! Supported types: mongo, mysql, mariadb, postgresql");
        }
        dataStore.connect();
    }

    @Override
    public void onDisable() {
        dataStore.disconnect();
    }

    public DataStore getDataStore() {
        return dataStore;
    }

}
