package es.caib.dp3t.ibcovid.codegen.controller.client.auth;

import es.caib.dp3t.ibcovid.codegen.controller.client.auth.model.LoginRQDto;
import es.caib.dp3t.ibcovid.codegen.controller.client.auth.model.LoginTokenDto;
import es.caib.dp3t.ibcovid.codegen.controller.client.auth.model.UserBasicDetailsDto;
import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;

import java.util.Map;

@Headers({"Accept: application/json", "Content-Type: application/json"})
public interface IBCovidAuthClient {

    @RequestLine("POST /auth/login")
    LoginTokenDto login(final LoginRQDto body);

    @RequestLine("GET /auth/authorize")
    UserBasicDetailsDto authorize(final @HeaderMap Map<String, Object> headers);

}
