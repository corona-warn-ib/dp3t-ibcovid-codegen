package es.caib.dp3t.ibcovid.codegen.controller.mapper;

import es.caib.dp3t.ibcovid.codegen.controller.model.GetAuthenticationTokenRQDto;
import es.caib.dp3t.ibcovid.codegen.controller.model.GetAuthenticationTokenRSDto;
import es.caib.dp3t.ibcovid.codegen.service.model.AuthenticationTokenSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.GetAuthenticationTokenRQSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationTokenDtoMapper {

    AuthenticationTokenDtoMapper INSTANCE = Mappers.getMapper(AuthenticationTokenDtoMapper.class);

    GetAuthenticationTokenRQSrvDto requestToSrvRequest(final GetAuthenticationTokenRQDto dto);

    GetAuthenticationTokenRSDto srvDtoToDto(final AuthenticationTokenSrvDto srvDto);

}
