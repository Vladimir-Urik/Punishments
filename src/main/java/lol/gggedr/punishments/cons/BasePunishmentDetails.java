package lol.gggedr.punishments.cons;

public record BasePunishmentDetails(
        String nickname,
        String reason,
        String punisher,
        long duration
) {
}
