package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.common.exception.SediaInvalidSignatureException;
import es.caib.dp3t.ibcovid.codegen.controller.client.codes.model.CodesResult;
import es.caib.dp3t.ibcovid.codegen.data.DownloadedAccessCodeRepository;
import es.caib.dp3t.ibcovid.codegen.data.model.DownloadedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.DownloadedAccessCodeService;
import es.caib.dp3t.ibcovid.codegen.service.RadarCovidClient;
import es.caib.dp3t.ibcovid.codegen.service.mapper.DownloadAccesCodeSrvDtoMapper;
import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Log4j2
public class DownloadedAccessCodeServiceImpl implements DownloadedAccessCodeService {

    private final DownloadedAccessCodeRepository downloadedAccessCodeRepository;
    private final EntityManager entityManager;
    private final ConversionService conversionService;
    private final RadarCovidClient radarCovidClient;

    private final String external;

    public DownloadedAccessCodeServiceImpl(final EntityManager entityManager,
                                           final DownloadedAccessCodeRepository downloadedAccessCodeRepository,
                                           final ConversionService conversionService,
                                           final RadarCovidClient radarCovidClient,
                                           @Value("${external.application.properties}") final String external) {
        this.downloadedAccessCodeRepository = downloadedAccessCodeRepository;
        this.entityManager = entityManager;
        this.conversionService = conversionService;
        this.radarCovidClient = radarCovidClient;
        this.external = external;
    }

    @Override
    @Transactional
    public DownloadedAccessCodeSrvDto getDownloadedAccessCode() {

        log.debug("BEGIN - createExposedAccessCode.");
        DownloadedAccessCodeSrvDto result = null;
        DownloadedAccessCode code = this.findCodeWithLock();
        if (code != null) {
            code.setActivatedAt(LocalDateTime.now());
            this.entityManager.merge(code);
            result = DownloadAccesCodeSrvDtoMapper.INSTANCE.entityToSrvDto(code);
        }
        log.debug("END - createExposedAccessCode.");
        return result;
    }

    @Override
    public List<DownloadedAccessCodeSrvDto> getDownloadedAccessCodeList() {
        List<DownloadedAccessCodeSrvDto> downloadedAccessCodeSrvDtoList = new ArrayList<>();
        List<DownloadedAccessCode> downloadedAccessCodeList =  downloadedAccessCodeRepository.findAllByActivatedAtIsNull();
        downloadedAccessCodeList.stream().forEach(x->downloadedAccessCodeSrvDtoList.add(DownloadedAccessCodeSrvDto.builder()
        .accessCode(x.getAccessCode())
        .activatedAt(x.getActivatedAt())
        .build()));
        return downloadedAccessCodeSrvDtoList;
    }

    @Override
    public boolean generateCodes() throws SediaInvalidSignatureException {
        Properties prop = new Properties();
        Integer size = 5;
        try {
            prop.load(new FileInputStream(external));
            size = Integer.parseInt(prop.getProperty("application.radarcovid.size"));
        } catch (IOException e) {
            log.error("ERROR al recuperar el numero de codigos a solicitar a SEDIA");
        }
        ResponseEntity<CodesResult> resultResponseEntity = radarCovidClient.getCodes(size, Boolean.FALSE);
        if(resultResponseEntity.getStatusCode().is2xxSuccessful()){
            CodesResult codesResult = resultResponseEntity.getBody();
            codesResult.getCodes().stream().forEach(code->{
                DownloadedAccessCode downloadedAccessCode = DownloadedAccessCode.builder()
                        .accessCode(code)
                        .createdAt(LocalDateTime.now())
                        .build();
                this.downloadedAccessCodeRepository.saveAndFlush(downloadedAccessCode);
            });
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public ResponseEntity<CodesResult> generateCodesTest(final Integer codeNumber, final boolean testToken) throws SediaInvalidSignatureException {
        return radarCovidClient.getCodes(codeNumber, testToken);
    }

    private DownloadedAccessCode findCodeWithLock(){
        Query query = entityManager
                .createQuery("select code from DownloadedAccessCode code where code.activatedAt is null order by code.createdAt asc");
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        final List<DownloadedAccessCode> codes = query.setMaxResults(1).getResultList();
        if(!codes.isEmpty()){
            return codes.get(0);
        }
        return null;
    }

}
