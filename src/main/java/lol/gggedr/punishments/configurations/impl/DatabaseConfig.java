package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;

@ConfigInfo(fileName = "database.yml")
public class DatabaseConfig {

    @ConfigField(path = "host")
    private String host = "localhost";

    @ConfigField(path = "port")
    private int port = 3306;

    @ConfigField(path = "collection")
    private String collection = "punishments";

    @ConfigField(path = "user")
    private String user = "root";

    @ConfigField(path = "password")
    private String password = "password";

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
