package eze.dev.valdeSync.Managers.DataBase.Schemes;

import org.bson.Document;
import java.util.UUID;

public class Player {
    private final UUID uuid;
    private final String name;
    private String discordId;
    private String code;
    private Boolean synced;
    private String role;

    public Player(UUID uuid, String name, String discordId, String code, Boolean synced, String role) {
        this.uuid = uuid;
        this.name = name;
        this.discordId = discordId;
        this.code = code;
        this.synced = synced;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public void setRole(String id) {
        this.role = id;
    }

    public String getDiscordId() {
        return discordId;
    }

    public Document toDocument() {
        return new Document("uuid", uuid.toString())
                .append("name", name)
                .append("discordId", discordId)
                .append("code", code)
                .append("synced", synced)
                .append("role", role);
    }
    public static Player fromDocument(Document doc) {
        UUID uuid = UUID.fromString(doc.getString("uuid"));
        String name = doc.getString("name");
        String discordId = doc.getString("discordId");
        String code = null;
        Boolean synced = false;
        String role = null;

        return new Player(uuid, name, discordId, code, synced, role);
    }
}
