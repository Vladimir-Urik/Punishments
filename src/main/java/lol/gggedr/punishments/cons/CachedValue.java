package lol.gggedr.punishments.cons;

import java.util.Objects;

public final class CachedValue<T> {

    private T value;
    private long time;
    private long duration;

    public CachedValue(T value, long time, long duration) {
        this.value = value;
        this.time = time;
        this.duration = duration;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - time >= duration;
    }

    public T getValue() {
        return value;
    }

    public T value() {
        return value;
    }

    public long time() {
        return time;
    }

    public long duration() {
        return duration;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
