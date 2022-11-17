package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.utils.StringUtils;

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

    @ConfigField(path = "commands.mute.usage")
    private String muteCommandUsage = "&cUsage: /mute <player> [duration] [reason] [-s]";

    @ConfigField(path = "commands.ban.usage")
    private String banCommandUsage = "&cUsage: /ban <player> [duration] [reason] [-s]";

    @ConfigField(path = "commands.unmute.usage")
    private String unmuteCommandUsage = "&cUsage: /unmute <player> [-s]";

    @ConfigField(path = "commands.unban.usage")
    private String unbanCommandUsage = "&cUsage: /unban <player> [-s]";

    @ConfigField(path = "commands.history.usage")
    private String historyCommandUsage = "&cUsage: /history <player>";

    @ConfigField(path = "no-permission")
    private String noPermissionMessage = "&cYou don't have permission to execute this command.";

    @ConfigField(path = "player-not-found")
    private String playerNotFoundMessage = "&cPlayer not found.";

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
}
