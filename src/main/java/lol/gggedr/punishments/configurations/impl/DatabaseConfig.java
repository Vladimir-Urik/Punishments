package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;

@ConfigInfo(fileName = "database.yml")
public class DatabaseConfig {

    @ConfigField(path = "host", defaultValue = "localhost")
    private String host;

    @ConfigField(path = "port", defaultValue = "3306")
    private int port;

    @ConfigField(path = "collection", defaultValue = "collection")
    private String collection;

    @ConfigField(path = "user", defaultValue = "root")
    private String user;

    @ConfigField(path = "password", defaultValue = "password")
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getCollection() {
        return collection;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
