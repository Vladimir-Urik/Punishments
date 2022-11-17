package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.utils.StringUtils;

@ConfigInfo(fileName = "messages.yml")
public class MessagesConfig implements Config {

    @ConfigField(path = "commands.warn.usage", defaultValue = "&cUsage: /warn <player> <reason>")
    private String warnCommandUsage;

    @ConfigField(path = "commands.kick.usage", defaultValue = "&cUsage: /kick <player> [reason] [-s]")
    private String kickCommandUsage;

    @ConfigField(path = "commands.mute.usage", defaultValue = "&cUsage: /mute <player> [duration] [reason] [-s]")
    private String muteCommandUsage;

    @ConfigField(path = "commands.ban.usage", defaultValue = "&cUsage: /ban <player> [duration] [reason] [-s]")
    private String banCommandUsage;

    @ConfigField(path = "commands.unmute.usage", defaultValue = "&cUsage: /unmute <player> [-s]")
    private String unmuteCommandUsage;

    @ConfigField(path = "commands.unban.usage", defaultValue = "&cUsage: /unban <player> [-s]")
    private String unbanCommandUsage;

    @ConfigField(path = "commands.history.usage", defaultValue = "&cUsage: /history <player>")
    private String historyCommandUsage;

    @ConfigField(path = "no-permission", defaultValue = "&cYou don't have permission to execute this command.")
    private String noPermissionMessage;

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
}
