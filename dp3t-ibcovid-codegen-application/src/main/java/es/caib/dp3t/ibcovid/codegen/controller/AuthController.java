package es.caib.dp3t.ibcovid.codegen.controller;

import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.codegen.service.AuthService;
import es.caib.dp3t.ibcovid.codegen.service.model.KeyCloakTokenRequestSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.KeycloakAuthenticationTokenSrvDto;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RouteConstants.AUTH_TOKEN_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Downloaded code administration operations")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public KeycloakAuthenticationTokenSrvDto getKeyClockToken(@RequestBody final KeyCloakTokenRequestSrvDto request){
        log.debug("BEGIN - getAuthenticationToken: params={}"
                , request.getUsername(), request.getPassword());
        final KeycloakAuthenticationTokenSrvDto token =  this.authService
                .getKeyCloakToken(request.getUsername(), request.getPassword());
        log.debug("END - getAuthenticationToken: params={}"
                , request.getUsername(), request.getPassword());
        return token;
    }

}
