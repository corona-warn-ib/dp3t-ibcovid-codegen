package es.caib.dp3t.ibcovid.codegen.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KeycloakAuthenticationTokenSrvDto {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expires_in")
    Long expiresIn;
    @JsonProperty("refresh_expires_in")
    Long refreshExpiresIn;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("not_before_policy")
    String notBeforePolicy;
    @JsonProperty("session_state")
    String sessionState;
    @JsonProperty("scope")
    String scope;
}
