package com.leoga.ecom.user.services;

import com.leoga.ecom.user.configuration.KeyCloakAdminData;
import com.leoga.ecom.user.dto.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class KeyCloakAdminService {

    private final KeyCloakAdminData keyCloakAdminData;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminAccessToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", keyCloakAdminData.getClientId());
        params.add("username", keyCloakAdminData.getUsername());
        params.add("password", keyCloakAdminData.getPassword());
        params.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = keyCloakAdminData.getServerUrl() + "/realms/" + keyCloakAdminData.getRealm() + "/protocol/openid-connect/token";
        ResponseEntity<Map> response = restTemplate.postForEntity(
                url,
                request,
                Map.class
        );


        return response.getBody().get("access_token").toString();
    }

    public String createUser(String token, UserRequest userRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", userRequest.getUserName());
        userPayload.put("email", userRequest.getEmail());
        userPayload.put("enabled", true);
        userPayload.put("firstName", userRequest.getFirstName());
        userPayload.put("lastName", userRequest.getLastName());

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value",  userRequest.getPassword());
        credentials.put("temporary", false);

        userPayload.put("credentials", List.of(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        String url = keyCloakAdminData.getServerUrl() + "/admin/realms/" + keyCloakAdminData.getRealm() + "/users";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class
        );

        if (!HttpStatus.CREATED.equals(response.getStatusCode())) {
            throw new RuntimeException("Failed to create user in keycloak : " + response.getBody());
        }

        // Extract keycloak user id
        URI location = response.getHeaders().getLocation();
        if (null == location) {
            throw new RuntimeException("Keycloak did not return Location Header " + response.getBody());
        }

        String path = location.getPath();

        return path.substring(path.lastIndexOf("/") + 1);
    }

    private Map<String, Object> getRealmRoleRepresentation(String token, String roleName) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url = keyCloakAdminData.getServerUrl() + "/admin/realms/"
                + keyCloakAdminData.getRealm() + "/clients/" + keyCloakAdminData.getClientUuid()
                + "/roles/" + roleName;

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Map.class
        );

        return response.getBody();
    }

    public void assignRealmRoleToUser(String username, String roleName, String userId) {
        String token = getAdminAccessToken();
        Map<String, Object> roleRep = getRealmRoleRepresentation(token, roleName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(roleRep), headers);

        String url = keyCloakAdminData.getServerUrl() + "/admin/realms/"
                + keyCloakAdminData.getRealm() + "/users/" + userId + "/role-mappings/clients/"
                + keyCloakAdminData.getClientUuid();

        ResponseEntity<Void> response = restTemplate.postForEntity(
                url,
                request,
                Void.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to assign the role " + roleName +
                    " to the user " + username +
                    ": HTTP " + response.getStatusCode());
        }
    }
}
