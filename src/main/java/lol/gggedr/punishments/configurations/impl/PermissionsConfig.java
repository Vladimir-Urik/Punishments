package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;

@ConfigInfo(fileName = "permissions.yml")
public class PermissionsConfig implements Config {

    @ConfigField(path = "commands.ban.permanent", defaultValue = "punishments.ban.permanent")
    private String permanentBanCommandPermission;

    @ConfigField(path = "commands.ban.temp", defaultValue = "punishments.ban.temp")
    private String tempBanCommandPermission;

    @ConfigField(path = "commands.kick", defaultValue = "punishments.kick")
    private String kickCommandPermission;

    @ConfigField(path = "commands.mute.permanent", defaultValue = "punishments.mute.permanent")
    private String permanentMuteCommandPermission;

    @ConfigField(path = "commands.mute.temp", defaultValue = "punishments.mute.temp")
    private String tempMuteCommandPermission;

    @ConfigField(path = "commands.warn", defaultValue = "punishments.warn")
    private String warnCommandPermission;

    @ConfigField(path = "commands.unban", defaultValue = "punishments.unban")
    private String unbanCommandPermission;

    @ConfigField(path = "commands.unmute", defaultValue = "punishments.unmute")
    private String unmuteCommandPermission;

    @ConfigField(path = "commands.history", defaultValue = "punishments.history")
    private String historyCommandPermission;

    public String getPermanentBanCommandPermission() {
        return permanentBanCommandPermission;
    }

    public String getTempBanCommandPermission() {
        return tempBanCommandPermission;
    }

    public String getKickCommandPermission() {
        return kickCommandPermission;
    }

    public String getPermanentMuteCommandPermission() {
        return permanentMuteCommandPermission;
    }

    public String getTempMuteCommandPermission() {
        return tempMuteCommandPermission;
    }

    public String getWarnCommandPermission() {
        return warnCommandPermission;
    }

    public String getUnbanCommandPermission() {
        return unbanCommandPermission;
    }

    public String getUnmuteCommandPermission() {
        return unmuteCommandPermission;
    }

    public String getHistoryCommandPermission() {
        return historyCommandPermission;
    }
}
