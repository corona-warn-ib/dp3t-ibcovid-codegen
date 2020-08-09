package es.caib.dp3t.ibcovid.codegen.service.mapper;

import es.caib.dp3t.ibcovid.codegen.data.model.DownloadedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DownloadAccesCodeSrvDtoMapper {

    DownloadAccesCodeSrvDtoMapper INSTANCE = Mappers.getMapper(DownloadAccesCodeSrvDtoMapper.class);

    DownloadedAccessCodeSrvDto entityToSrvDto(final DownloadedAccessCode entity);


}
