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
    private long end;
    private final PunishmentType type;

    public Punishment(
            String _id,
            String nickname,
            String reason,
            String issuer,
            long start,
            long end,
            PunishmentType type
    ) {
        this._id = _id;
        this.nickname = nickname;
        this.reason = reason;
        this.issuer = issuer;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public void insert() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        var result = collection.insert(new BasicDBObject()
                .append("nickname", nickname)
                .append("reason", reason)
                .append("issuer", issuer)
                .append("start", start)
                .append("end", end)
                .append("type", type.name())
        );

        this._id = (String) result.getUpsertedId();
    }

    public void update() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        collection.update(new BasicDBObject("_id", _id), new BasicDBObject()
                .append("nickname", nickname)
                .append("reason", reason)
                .append("issuer", issuer)
                .append("start", start)
                .append("end", end)
                .append("type", type.name())
        );
    }

    public static Punishment fromDocument(DBObject object) {
        return new Punishment(
                (String) object.get("_id"),
                (String) object.get("nickname"),
                (String) object.get("reason"),
                (String) object.get("issuer"),
                (long) object.get("start"),
                (long) object.get("end"),
                PunishmentType.valueOf((String) object.get("type"))
        );
    }

    public void delete() {
        var collection = Managers.getManager(DatabaseManager.class).getCollection();
        collection.remove(new BasicDBObject("_id", _id));
    }

    public String _id() {
        return _id;
    }

    public String nickname() {
        return nickname;
    }

    public String reason() {
        return reason;
    }

    public String issuer() {
        return issuer;
    }

    public long start() {
        return start;
    }

    public long end() {
        return end;
    }

    public PunishmentType type() {
        return type;
    }

}
