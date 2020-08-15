package es.caib.dp3t.ibcovid.codegen.service;

import es.caib.dp3t.ibcovid.codegen.service.model.KeycloakAuthenticationTokenSrvDto;

public interface AuthService {

    KeycloakAuthenticationTokenSrvDto getKeyCloakToken(final String username, final String password);

}
