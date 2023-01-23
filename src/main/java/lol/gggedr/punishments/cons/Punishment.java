package lol.gggedr.punishments.cons;

import com.mongodb.BasicDBObject;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.DatabaseManager;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public final class Punishment {

    private ObjectId id;
    private final String nickname;
    private final String reason;
    private final String issuer;
    private final long start;
    private final long end;
    private final PunishmentType type;
    private boolean active;
    private String removedBy;
    private String removeReason;

    private List<String> updates = new ArrayList<>();

    public Punishment(
            ObjectId _id,
            String nickname,
            String reason,
            String issuer,
            long start,
            long end,
            PunishmentType type,
            boolean active,
            String removedBy,
            String removeReason
    ) {
        this.id = _id;
        this.nickname = nickname;
        this.reason = reason;
        this.issuer = issuer;
        this.start = start;
        this.end = end;
        this.type = type;
        this.active = active;
        this.removedBy = removedBy;
        this.removeReason = removeReason;
    }

    /**
     * It creates a new document, iterates over the updates list, and adds each field to the document
     *
     * @return A Document object that contains the updates to the object.
     */
    public Document toUpdateDocument() {
        var document = new Document();
        for(var update : updates) {
            try {
                var field = getClass().getDeclaredField(update);
                field.setAccessible(true);
                document.append(update, field.get(this));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        updates.clear();
        return document;
    }

    public Document toDocument() {
        return new Document()
                .append("_id", id)
                .append("nickname", nickname)
                .append("reason", reason)
                .append("issuer", issuer)
                .append("start", start)
                .append("end", end)
                .append("type", type.name())
                .append("active", active)
                .append("removedBy", removedBy)
                .append("removeReason", removeReason);
    }

    /**
     * It takes a document from the database and converts it into a Punishment object
     *
     * @param document The document that you want to convert to a Punishment object.
     * @return A new Punishment object.
     */
    public static Punishment fromDocument(Document document) {
        return new Punishment(
                document.getObjectId("_id"),
                document.getString("nickname"),
                document.getString("reason"),
                document.getString("issuer"),
                document.getLong("start"),
                document.getLong("end"),
                PunishmentType.valueOf(document.getString("type")),
                document.getBoolean("active"),
                document.getString("removedBy"),
                document.getString("removeReason")
        );
    }

    /**
     * > This function returns the value of the _id field
     *
     * @return The _id of the document.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * This function returns the nickname of the user.
     *
     * @return The nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the reason for the failure.
     *
     * @return The reason for the exception.
     */
    public String getReason() {
        return reason;
    }

    /**
     * > This function returns the issuer of the card
     *
     * @return The issuer of the card.
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * This function returns the start time of the timer.
     *
     * @return The start time of the stopwatch.
     */
    public long getStart() {
        return start;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time of the event.
     */
    public long getEnd() {
        return end;
    }

    /**
     * `Returns the type of punishment.`
     *
     * @return The type of punishment.
     */
    public PunishmentType getType() {
        return type;
    }

    /**
     * Returns true if the object is active, false otherwise.
     *
     * @return The boolean value of the variable active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This function sets the active variable to the value of the active parameter.
     *
     * @param active This is a boolean value that determines whether the user is active or not.
     */
    public void setActive(boolean active) {
        this.active = active;
        update("active");
    }

    /**
     * > This function returns the name of the user who removed the file
     *
     * @return The removedBy variable is being returned.
     */
    public String getRemovedBy() {
        return removedBy;
    }


    /**
     * This function sets the removedBy field to the value of the removedBy parameter.
     *
     * @param removedBy The name of the user who removed the item.
     */
    public void setRemovedBy(String removedBy) {
        this.removedBy = removedBy;
        update("removedBy");
    }


    /**
     * This function returns the reason for the removal of the user
     *
     * @return The removeReason variable is being returned.
     */
    public String getRemoveReason() {
        return removeReason;
    }


    /**
     * This function sets the removeReason variable to the value of the removeReason parameter.
     *
     * @param removeReason The reason for the removal.
     */
    public void setRemoveReason(String removeReason) {
        this.removeReason = removeReason;
        update("removeReason");
    }

    private void update(String field) {
        if(!updates.contains(field)) {
            updates.add(field);
        }
    }
}
