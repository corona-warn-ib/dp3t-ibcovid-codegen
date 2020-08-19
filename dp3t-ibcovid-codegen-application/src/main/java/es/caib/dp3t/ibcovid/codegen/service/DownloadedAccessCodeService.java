package es.caib.dp3t.ibcovid.codegen.service;

import es.caib.dp3t.ibcovid.codegen.controller.client.codes.model.CodesResult;
import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DownloadedAccessCodeService {

    DownloadedAccessCodeSrvDto getDownloadedAccessCode();

    List<DownloadedAccessCodeSrvDto> getDownloadedAccessCodeList();

    boolean generateCodes();

    ResponseEntity<CodesResult> generateCodesTest(final Integer codeNumber, final boolean testToken);
}
