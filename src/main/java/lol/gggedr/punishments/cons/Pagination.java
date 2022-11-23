package lol.gggedr.punishments.cons;

import java.util.Objects;

public final class Pagination {
    private final int from;
    private final int to;
    private int page = 1;

    public Pagination(
            int from,
            int to
    ) {
        this.from = from;
        this.to = to;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public int page() {
        return page;
    }

    public void page(int page) {
        this.page = page;
    }

}
