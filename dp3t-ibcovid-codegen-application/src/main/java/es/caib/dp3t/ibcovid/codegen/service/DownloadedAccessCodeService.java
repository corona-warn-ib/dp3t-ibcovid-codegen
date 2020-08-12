package es.caib.dp3t.ibcovid.codegen.service;

import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;

import java.util.List;

public interface DownloadedAccessCodeService {

    DownloadedAccessCodeSrvDto getDownloadedAccessCode();

    List<DownloadedAccessCodeSrvDto> getDownloadedAccessCodeList();

    boolean generateCodes();
}
