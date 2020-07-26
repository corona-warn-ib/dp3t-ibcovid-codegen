package es.caib.dp3t.ibcovid.codegen.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetAuthenticationTokenRQSrvDto {
    String authorizationCode;
    int fake;

}
