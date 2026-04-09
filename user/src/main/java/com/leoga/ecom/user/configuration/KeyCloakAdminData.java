package com.leoga.ecom.user.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak.admin")
@Data
public class KeyCloakAdminData {

    private String username;
    private String password;
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientUuid;
}
