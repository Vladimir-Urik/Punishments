package lol.gggedr.punishments.cons;

public record BasePunishmentDetails(
        String nickname,
        String reason,
        String issuer,
        long duration,
        boolean silent
) {

    public boolean isPermanent() {
        return duration == 0;
    }

}
