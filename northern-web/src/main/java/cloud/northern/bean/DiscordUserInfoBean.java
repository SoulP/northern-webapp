package cloud.northern.bean;

import org.springframework.stereotype.Component;

/**
 * Discord user info
 *
 * @author SoulP
 *
 */
@Component
public class DiscordUserInfoBean {
    private String  id;
    private String  username;
    private String  discriminator;
    private String  avatar;
    private Boolean bot;
    private Boolean mfa_enabled;
    private String  locale;
    private Boolean verified;
    private String  email;
    private Integer flags;
    private Integer premium_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public Boolean getMfa_enabled() {
        return mfa_enabled;
    }

    public void setMfa_enabled(Boolean mfa_enabled) {
        this.mfa_enabled = mfa_enabled;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getPremium_type() {
        return premium_type;
    }

    public void setPremium_type(Integer premium_type) {
        this.premium_type = premium_type;
    }
}
