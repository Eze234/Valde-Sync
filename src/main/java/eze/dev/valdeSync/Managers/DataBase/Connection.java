package eze.dev.valdeSync.Managers.DataBase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bukkit.ChatColor;

import eze.dev.valdeSync.Core;
import eze.dev.valdeSync.Utils.utils;

public class Connection {
    private static Connection instance;
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public Connection() {
        ConnectionString connectionString = new ConnectionString(Core.getInstance().getConfig().getString("db.connection"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase(Core.getInstance().getConfig().getString("db.collection"));
        instance = this;
    }

    public static Connection getInstance() {
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        utils.console(utils.colorMsg(ChatColor.BLUE + "[Valde-Sync] " + ChatColor.RED + "Base de datos apagada"));
        mongoClient.close();
    }
}
