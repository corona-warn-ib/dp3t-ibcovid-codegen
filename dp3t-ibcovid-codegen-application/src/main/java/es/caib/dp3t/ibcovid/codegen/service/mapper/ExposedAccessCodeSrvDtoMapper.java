package es.caib.dp3t.ibcovid.codegen.service.mapper;

import es.caib.dp3t.ibcovid.codegen.data.model.ExposedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.model.ExposedAccessCodeSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExposedAccessCodeSrvDtoMapper {

    ExposedAccessCodeSrvDtoMapper INSTANCE = Mappers.getMapper(ExposedAccessCodeSrvDtoMapper.class);

    ExposedAccessCodeSrvDto entityToSrvDto(final ExposedAccessCode entity);

}
