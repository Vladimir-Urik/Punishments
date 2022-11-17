package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;

@ConfigInfo(fileName = "permissions.yml")
public class PermissionsConfig implements Config {

    @ConfigField(path = "commands.ban.permanent")
    private String permanentBanCommandPermission = "punishments.ban.permanent";

    @ConfigField(path = "commands.ban.temp")
    private String tempBanCommandPermission = "punishments.ban.temp";

    @ConfigField(path = "commands.kick")
    private String kickCommandPermission = "punishments.kick";

    @ConfigField(path = "commands.mute.permanent")
    private String permanentMuteCommandPermission = "punishments.mute.permanent";

    @ConfigField(path = "commands.mute.temp")
    private String tempMuteCommandPermission = "punishments.mute.temp";

    @ConfigField(path = "commands.warn")
    private String warnCommandPermission = "punishments.warn";

    @ConfigField(path = "commands.unban")
    private String unbanCommandPermission = "punishments.unban";

    @ConfigField(path = "commands.unmute")
    private String unmuteCommandPermission = "punishments.unmute";

    @ConfigField(path = "commands.history")
    private String historyCommandPermission = "punishments.history";

    @ConfigField(path = "silent.by-pass")
    private String silentByPassPermission = "punishments.silent.bypass";

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

    public String getSilentByPassPermission() {
        return silentByPassPermission;
    }
}
