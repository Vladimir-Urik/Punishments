package lol.gggedr.punishments.cons;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lol.gggedr.punishments.enums.PunishmentType;
import lol.gggedr.punishments.managers.Managers;
import lol.gggedr.punishments.managers.impl.DatabaseManager;

import java.util.Objects;

public final class Punishment {

    private String _id;
    private final String nickname;
    private final String reason;
    private final String issuer;
    private final long start;
    private final long end;
    private final PunishmentType type;
    private boolean active;
    private String unbannedBy;
    private String unbannedReason;

    public Punishment(
            String _id,
            String nickname,
            String reason,
            String issuer,
            long start,
            long end,
            PunishmentType type,
            boolean active,
            String unbannedBy,
            String unbannedReason
    ) {
        this._id = _id;
        this.nickname = nickname;
        this.reason = reason;
        this.issuer = issuer;
        this.start = start;
        this.end = end;
        this.type = type;
        this.active = active;
        this.unbannedBy = unbannedBy;
        this.unbannedReason = unbannedReason;
    }

    /**
     * It inserts a new document into the database
     */
    public void insert() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        var result = collection.insert(new BasicDBObject()
                .append("nickname", nickname)
                .append("reason", reason)
                .append("issuer", issuer)
                .append("start", start)
                .append("end", end)
                .append("type", type.name())
                .append("active", active)
                .append("unbannedBy", unbannedBy)
                .append("unbannedReason", unbannedReason)
        );

        this._id = (String) result.getUpsertedId();
    }

    /**
     * It updates the database with the current values of the object
     */
    public void update() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        collection.update(new BasicDBObject("_id", _id), new BasicDBObject()
                .append("nickname", nickname)
                .append("reason", reason)
                .append("issuer", issuer)
                .append("start", start)
                .append("end", end)
                .append("type", type.name())
                .append("active", active)
                .append("unbannedBy", unbannedBy)
                .append("unbannedReason", unbannedReason)
        );
    }

    /**
     * It takes a DBObject and returns a Punishment object
     *
     * @param object The document from the database
     * @return A new Punishment object.
     */
    public static Punishment fromDocument(DBObject object) {
        return new Punishment(
                (String) object.get("_id"),
                (String) object.get("nickname"),
                (String) object.get("reason"),
                (String) object.get("issuer"),
                (long) object.get("start"),
                (long) object.get("end"),
                PunishmentType.valueOf((String) object.get("type")),
                (boolean) object.get("active"),
                (String) object.get("unbannedBy"),
                (String) object.get("unbannedReason")
        );
    }

    /**
     * "Delete the document from the database with the same _id as this object."
     * <p>
     * The first line gets the collection from the database. The second line deletes the document with the same _id as this
     * object
     */
    public void delete() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        collection.remove(new BasicDBObject("_id", _id));
    }

    /**
     * > This function returns the value of the _id field
     *
     * @return The _id of the document.
     */
    public String _id() {
        return _id;
    }

    /**
     * This function returns the nickname of the user.
     *
     * @return The nickname of the user.
     */
    public String nickname() {
        return nickname;
    }

    /**
     * Returns the reason for the failure.
     *
     * @return The reason for the exception.
     */
    public String reason() {
        return reason;
    }

    /**
     * > This function returns the issuer of the card
     *
     * @return The issuer of the card.
     */
    public String issuer() {
        return issuer;
    }

    /**
     * This function returns the start time of the timer.
     *
     * @return The start time of the stopwatch.
     */
    public long start() {
        return start;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time of the event.
     */
    public long end() {
        return end;
    }

    /**
     * `Returns the type of punishment.`
     *
     * @return The type of punishment.
     */
    public PunishmentType type() {
        return type;
    }

    /**
     * Returns true if the object is active, false otherwise.
     *
     * @return The boolean value of the variable active.
     */
    public boolean active() {
        return active;
    }

    /**
     * This function sets the active variable to the value of the active parameter.
     *
     * @param active This is a boolean value that determines whether the user is active or not.
     */
    public void active(boolean active) {
        this.active = active;
    }

    /**
     * This function returns the name of the user who unbanned the user
     *
     * @return The name of the user who unbanned the user.
     */
    public String unbannedBy() {
        return unbannedBy;
    }

    /**
     * This function sets the value of the variable `unbannedBy` to the value of the parameter `unbannedBy`
     *
     * @param unbannedBy The name of the user who unbanned the user.
     */
    public void unbannedBy(String unbannedBy) {
        this.unbannedBy = unbannedBy;
    }

    /**
     * It returns the reason why the user was unbanned
     *
     * @return The unbanned reason.
     */
    public String unbannedReason() {
        return unbannedReason;
    }

    /**
     * This function sets the reason for the user being unbanned
     *
     * @param unbannedReason The reason for the unban.
     */
    public void unbannedReason(String unbannedReason) {
        this.unbannedReason = unbannedReason;
    }

}
