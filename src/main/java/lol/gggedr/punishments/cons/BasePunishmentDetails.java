package lol.gggedr.punishments.cons;

public record BasePunishmentDetails(
        String nickname,
        String reason,
        String punisher,
        long duration
) {

    public boolean isPermanent() {
        return duration == 0;
    }

}
