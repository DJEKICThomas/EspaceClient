package be.depairon.espaceclient.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.yglobal")
public class CustomProperties {
    private String secret;
    private String mailgestionnaire;
    private String mailinscription;
    private String logo;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMailgestionnaire() {
        return mailgestionnaire;
    }

    public void setMailgestionnaire(String mailgestionnaire) {
        this.mailgestionnaire = mailgestionnaire;
    }

    public String getMailinscription() {
        return mailinscription;
    }

    public void setMailinscription(String mailinscription) {
        this.mailinscription = mailinscription;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
