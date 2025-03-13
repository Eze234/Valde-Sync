package eze.dev.valdeSync.Managers.DataBase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.UUID;
import eze.dev.valdeSync.Managers.DataBase.Schemes.Player;

public class PlayerDataManager {
    private final MongoCollection<Document> collection;

    public PlayerDataManager() {
        MongoDatabase database = Connection.getInstance().getDatabase();
        this.collection = database.getCollection("players");
    }

    public void savePlayer(Player player) {
        collection.insertOne(player.toDocument());
    }

    public Player getPlayerByDiscordId(String discordId) {
        Document doc = collection.find(eq("discordId", discordId)).first();
        return (doc != null) ? Player.fromDocument(doc) : null;
    }

    public void updateCode(String discordId, String newCode) {
        collection.updateOne(eq("discordId", discordId), new Document("$set", new Document("code", newCode)));
    }

    public void updateSynced(String discordId, boolean synced) {
        collection.updateOne(eq("discordId", discordId), new Document("$set", new Document("synced", synced)));
    }

    public void updateRole(UUID uuid, String role) {
        collection.updateOne(eq("uuid", uuid.toString()), new Document("$set", new Document("role", role)));
    }

    public boolean playerExists(UUID uuid) {
        return collection.find(eq("uuid", uuid.toString())).first() != null;
    }

    public boolean onDiscordConnected(String id) {
        return collection.find(eq("discordId", id)).first() != null;
    }

    public boolean getSynced(UUID uuid) {
        Document doc = collection.find(eq("uuid", uuid.toString())).first();
        return doc != null && doc.getBoolean("synced", false);
    }

    public String getDiscordId(UUID uuid) {
        Document doc = collection.find(eq("uuid", uuid.toString())).first();
        return doc.getString("discordId");
    }

    public void setSynced(UUID uuid, String role) {
        collection.updateOne(eq("uuid", uuid.toString()),
                new Document("$set", new Document("synced", true).append("role", role)));
    }

    public Boolean equalCode(UUID uuid, String code) {
        Document doc = collection.find(eq("uuid", uuid.toString())).first();
        return doc != null && code.equals(doc.getString("code"));
    }

    public String getRank(UUID uuid) {
        Document doc = collection.find(eq("uuid", uuid.toString())).first();
        return doc.getString("role");
    }
}
