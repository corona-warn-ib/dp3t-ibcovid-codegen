package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.service.AuthService;
import es.caib.dp3t.ibcovid.codegen.service.model.KeycloakAuthenticationTokenSrvDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String USER = "username";
    private static final String PASSWORD= "password";
    private static final String GRANT_TYPE = "grant_type";
    @Value("${keycloak.url}")
    private String keyCloakUrl;
    @Value("${keycloak.clientId}")
    private String keyCloakClientId;
    @Value("${keycloak.clientSecret}")
    private String keyCloakClientSecret;
    @Value("${keycloak.grantType}")
    private String keyCloakGrantType;

    private final RestTemplate restTemplate;

    public AuthServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public KeycloakAuthenticationTokenSrvDto getKeyCloakToken(final String username, final String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID,this.keyCloakClientId);
        map.add(CLIENT_SECRET,this.keyCloakClientSecret);
        map.add(USER, username);
        map.add(PASSWORD, password);
        map.add(GRANT_TYPE, keyCloakGrantType);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<KeycloakAuthenticationTokenSrvDto> response = restTemplate.postForEntity(
                keyCloakUrl,
                entity,
                KeycloakAuthenticationTokenSrvDto.class);
        return response.getBody();
    }

}
