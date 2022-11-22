package lol.gggedr.punishments.cons;

public record BasePunishmentDetails(
        String nickname,
        String reason,
        String issuer,
        long duration,
        boolean silent
) {

    // It's a method that returns true if the duration is 0.
    public boolean isPermanent() {
        return duration == -1;
    }

}
