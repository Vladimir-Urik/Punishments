package lol.gggedr.punishments.configurations.impl;

import lol.gggedr.punishments.configurations.Config;
import lol.gggedr.punishments.configurations.annotations.ConfigField;
import lol.gggedr.punishments.configurations.annotations.ConfigInfo;
import lol.gggedr.punishments.utils.StringUtils;

import java.util.List;

@ConfigInfo(fileName = "layouts.yml")
public class LayoutsConfig implements Config {

    @ConfigField(path = "appeal")
    private List<String> appeal = List.of(
            "&9Do you want to appeal your ban?",
            "&9Discord: &fhttps://discord.gg/invite"
    );

    @ConfigField(path = "ban.permanent")
    private List<String> banPermanent = List.of(
            "&cYou have been banned from this server.",
            "&c ",
            "&cReason: &f%reason%",
            "&cBanned by: &f%issuer%",
            "&c ",
            "&f%appeal%"
    );

    @ConfigField(path = "ban.temp")
    private List<String> banTemp = List.of(
            "&cYou have been banned from this server.",
            "&c ",
            "&cReason: &f%reason%",
            "&cBanned by: &f%issuer%",
            "&cExpires in: &f%expires%",
            "&c ",
            "&f%appeal%"
    );

    @ConfigField(path = "kick")
    private List<String> kick = List.of(
            "&cYou have been kicked from this server.",
            "&c ",
            "&cReason: &f%reason%",
            "&cKicked by: &f%issuer%"
    );

    public String getAppeal() {
        return StringUtils.toString(appeal);
    }

    public String getBanPermanent(String reason, String issuer) {
        return StringUtils.toString(banPermanent)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer)
                .replace("%appeal%", getAppeal());
    }

    public String getBanTemp(String reason, String issuer, String expires) {
        return StringUtils.toString(banTemp)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer)
                .replace("%expires%", expires)
                .replace("%appeal%", getAppeal());
    }

    public String getKick(String reason, String issuer) {
        return StringUtils.toString(kick)
                .replace("%reason%", reason)
                .replace("%issuer%", issuer);
    }

}
