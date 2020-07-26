package es.caib.dp3t.ibcovid.codegen.controller.mapper;

import es.caib.dp3t.ibcovid.codegen.common.mapper.DateEpochMapper;
import es.caib.dp3t.ibcovid.codegen.controller.model.CreateExposedAccessCodeRQDto;
import es.caib.dp3t.ibcovid.codegen.controller.model.CreateExposedAccessCodeRSDto;
import es.caib.dp3t.ibcovid.codegen.service.model.CreateExposedAccessCodeRQSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.ExposedAccessCodeSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DateEpochMapper.class)
public interface ExposedAccessCodeDtoMapper {

    ExposedAccessCodeDtoMapper INSTANCE = Mappers.getMapper(ExposedAccessCodeDtoMapper.class);

    CreateExposedAccessCodeRQSrvDto requestToSrvRequest(final CreateExposedAccessCodeRQDto dto);

    CreateExposedAccessCodeRSDto srvDtoToDto(final ExposedAccessCodeSrvDto srvDto);

}
