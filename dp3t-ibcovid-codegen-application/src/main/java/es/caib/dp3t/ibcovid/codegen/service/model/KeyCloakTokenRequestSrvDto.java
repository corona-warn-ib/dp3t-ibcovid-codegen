package es.caib.dp3t.ibcovid.codegen.service.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class KeyCloakTokenRequestSrvDto {
    private String username;
    private String password;
}
