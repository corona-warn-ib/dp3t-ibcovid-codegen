package es.caib.dp3t.ibcovid.codegen.service;

import es.caib.dp3t.ibcovid.codegen.service.model.AuthenticationTokenSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.CreateExposedAccessCodeRQSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.ExposedAccessCodeSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.GetAuthenticationTokenRQSrvDto;

public interface CodeGenService {

    ExposedAccessCodeSrvDto createExposedAccessCode(final CreateExposedAccessCodeRQSrvDto request);

    AuthenticationTokenSrvDto getAuthenticationToken(final GetAuthenticationTokenRQSrvDto srvRequest);

}
