package com.example.EventService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class KeycloakUserService {
    private final RestTemplate restTemplate;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    public KeycloakUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public String getAdminToken() {
        String url = keycloakServerUrl + "/realms/master/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=password" +
                "&client_id=admin-cli" +
                "&username=" + adminUsername +
                "&password=" + adminPassword;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Erro ao obter token de admin no Keycloak");
        }
    }

    public boolean userExists(String userId) {
        String adminToken = getAdminToken();
        String url = keycloakServerUrl + "/admin/realms/" + realm + "/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

}
