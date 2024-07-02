package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.utils.StringUtils;

import java.util.HashMap;

@ConfigInfo(fileName = "messages.yml")
public class MessagesConfig implements Config {

    @ConfigField(path = "commands.warn.usage")
    private String warnCommandUsage = "&cUsage: /warn <player> <reason>";

    @ConfigField(path = "commands.warn.success")
    private String warnCommandSuccess = "&aSuccessfully warned &e%player% &afor &e%reason%";

    @ConfigField(path = "commands.warn.alert.public")
    private String warnCommandAlertPublic = "&e%player% &ahas been warned for &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.warn.alert.silent")
    private String warnCommandAlertSilent = "&e%player% &ahas been warned for &e%reason% &aby &e%issuer% &c(silent)";

    @ConfigField(path = "commands.warn.alert.target")
    private String warnCommandAlertTarget = "&aYou have warned &e%player% &afor &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.kick.usage")
    private String kickCommandUsage = "&cUsage: /kick <player> [reason] [-s]";

    @ConfigField(path = "commands.kick.success")
    private String kickCommandSuccess = "&aSuccessfully kicked &e%player% &afor &e%reason%";

    @ConfigField(path = "commands.kick.alert.public")
    private String kickCommandAlertPublic = "&e%player% &ahas been kicked for &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.kick.alert.silent")
    private String kickCommandAlertSilent = "&e%player% &ahas been kicked for &e%reason% &aby &e%issuer% &c(silent)";

    @ConfigField(path = "commands.ban.alert.public")
    private String banCommandAlertPublic = "&e%player% &ahas been banned for &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.ban.alert.silent")
    private String banCommandAlertSilent = "&e%player% &ahas been banned for &e%reason% &aby &e%issuer% &c(silent)";

    @ConfigField(path = "commands.mute.usage")
    private String muteCommandUsage = "&cUsage: /mute <player> [duration] [reason] [-s]";

    @ConfigField(path = "commands.mute.success")
    private String muteCommandSuccess = "&aSuccessfully muted &e%player% &afor &e%reason%";

    @ConfigField(path = "commands.mute.alert.public")
    private String muteCommandAlertPublic = "&e%player% &ahas been muted for &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.mute.alert.silent")
    private String muteCommandAlertSilent = "&e%player% &ahas been muted for &e%reason% &aby &e%issuer% &c(silent)";

    @ConfigField(path = "commands.mute.alert.target")
    private String muteCommandAlertTarget = "&aYou have been muted for &e%reason% &aby &e%issuer%";

    @ConfigField(path = "commands.ban.usage")
    private String banCommandUsage = "&cUsage: /ban <player> [duration] [reason] [-s]";

    @ConfigField(path = "commands.unmute.usage")
    private String unmuteCommandUsage = "&cUsage: /unmute <player> [reason]";

    @ConfigField(path = "commands.unmute.success")
    private String unmuteCommandSuccess = "&aSuccessfully unmuted &e%player% &afor &e%reason%";

    @ConfigField(path = "commands.unmute.player-not-muted")
    private String playerNotMuted = "&c%player% &cis not muted";

    @ConfigField(path = "commands.unban.usage")
    private String unbanCommandUsage = "&cUsage: /unban <player> [reason]";

    @ConfigField(path = "commands.unban.success")
    private String unbanCommandSuccess = "&aSuccessfully unbanned &e%player% &afor &e%reason%";

    @ConfigField(path = "commands.unban.player-not-banned")
    private String playerNotBanned = "&c%player% &cis not banned";

    @ConfigField(path = "commands.history.usage")
    private String historyCommandUsage = "&cUsage: /history <player>";

    @ConfigField(path = "commands.history.no-history")
    private String historyCommandNoHistory = "&c%player% has no history";

    @ConfigField(path = "no-permission")
    private String noPermissionMessage = "&cYou don't have permission to execute this command.";

    @ConfigField(path = "player-not-found")
    private String playerNotFoundMessage = "&cPlayer not found.";

    @ConfigField(path = "only-players")
    private String onlyPlayersMessage = "&cOnly players can execute this command.";

    public String getCommandUsage(String command) {
        return StringUtils.colorize(switch (command) {
            case "warn" -> warnCommandUsage;
            case "kick" -> kickCommandUsage;
            case "mute" -> muteCommandUsage;
            case "ban" -> banCommandUsage;
            case "unmute" -> unmuteCommandUsage;
            case "unban" -> unbanCommandUsage;
            case "history" -> historyCommandUsage;
            default -> "";
        });
    }

    public String getNoPermissionMessage() {
        return StringUtils.colorize(noPermissionMessage);
    }

    public String getPlayerNotFoundMessage() {
        return StringUtils.colorize(playerNotFoundMessage);
    }

    public String getWarnCommandSuccess(String player, String reason, String issuer) {
        return StringUtils.colorize(warnCommandSuccess)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getWarnCommandAlertPublic(String player, String reason, String issuer) {
        return StringUtils.colorize(warnCommandAlertPublic)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getWarnCommandAlertSilent(String player, String reason, String issuer) {
        return StringUtils.colorize(warnCommandAlertSilent)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getWarnCommandAlertTarget(String player, String reason, String issuer) {
        return StringUtils.colorize(warnCommandAlertTarget)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getKickCommandSuccess(String player, String reason, String issuer) {
        return StringUtils.colorize(kickCommandSuccess)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getKickCommandAlertPublic(String player, String reason, String issuer) {
        return StringUtils.colorize(kickCommandAlertPublic)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getKickCommandAlertSilent(String player, String reason, String issuer) {
        return StringUtils.colorize(kickCommandAlertSilent)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getMuteCommandSuccess(String player, String reason, String issuer) {
        return StringUtils.colorize(muteCommandSuccess)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getMuteCommandAlertPublic(String player, String reason, String issuer) {
        return StringUtils.colorize(muteCommandAlertPublic)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getMuteCommandAlertSilent(String player, String reason, String issuer) {
        return StringUtils.colorize(muteCommandAlertSilent)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getMuteCommandAlertTarget(String player, String reason, String issuer) {
        return StringUtils.colorize(muteCommandAlertTarget)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getHistoryCommandNoHistory(String player) {
        return StringUtils.colorize(historyCommandNoHistory)
                .replace("%player%", player);
    }

    public String getOnlyPlayersMessage() {
        return StringUtils.colorize(onlyPlayersMessage);
    }

    public String getUnbanCommandSuccess(String player, String reason) {
        var placeholders = new HashMap<String, Object>();
        placeholders.put("player", player);
        placeholders.put("reason", reason);

        return StringUtils.colorize(StringUtils.replacePlaceholders(unbanCommandSuccess, placeholders));
    }

    public String getPlayerNotBanned(String player) {
        return StringUtils.colorize(playerNotBanned)
                .replace("%player%", player);
    }

    public String getUnmuteCommandSuccess(String player, String reason) {
        var placeholders = new HashMap<String, Object>();
        placeholders.put("player", player);
        placeholders.put("reason", reason);

        return StringUtils.colorize(StringUtils.replacePlaceholders(unmuteCommandSuccess, placeholders));
    }

    public String getPlayerNotMuted(String player) {
        return StringUtils.colorize(playerNotMuted).replace("%player%", player);
    }

    public String getBanCommandAlertPublic(String player, String reason, String issuer) {
        return StringUtils.colorize(banCommandAlertPublic)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

    public String getBanCommandAlertSilent(String player, String reason, String issuer) {
        return StringUtils.colorize(banCommandAlertSilent)
                .replace("%player%", player)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }
}
