package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.common.config.AppProperties;
import es.caib.dp3t.ibcovid.codegen.common.exception.CodeGenErrorCodes;
import es.caib.dp3t.ibcovid.codegen.common.exception.IBCovidCodeGenException;
import es.caib.dp3t.ibcovid.codegen.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.codegen.data.ExposedAccessCodeRepository;
import es.caib.dp3t.ibcovid.codegen.data.model.ExposedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.CodeGenService;
import es.caib.dp3t.ibcovid.codegen.service.mapper.ExposedAccessCodeSrvDtoMapper;
import es.caib.dp3t.ibcovid.codegen.service.model.AuthenticationTokenSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.CreateExposedAccessCodeRQSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.ExposedAccessCodeSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.GetAuthenticationTokenRQSrvDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Log4j2
class CodeGenServiceImpl implements CodeGenService {

    private final AppProperties appProperties;
    private final ExposedAccessCodeRepository exposedAccessCodeRepository;
    private final AccessCodeGenerator exposedAccessCodeGenerator;
    private final AuthorizationTokenGenerator authorizationTokenGenerator;

    public CodeGenServiceImpl(final AppProperties appProperties,
                              final ExposedAccessCodeRepository exposedAccessCodeRepository,
                              final AccessCodeGenerator exposedAccessCodeGenerator,
                              final AuthorizationTokenGenerator authorizationTokenGenerator) {
        this.appProperties = appProperties;
        this.exposedAccessCodeRepository = exposedAccessCodeRepository;
        this.exposedAccessCodeGenerator = exposedAccessCodeGenerator;
        this.authorizationTokenGenerator = authorizationTokenGenerator;
    }

    @Override
    @Transactional
    public ExposedAccessCodeSrvDto createExposedAccessCode(final CreateExposedAccessCodeRQSrvDto request) {
        log.debug("BEGIN - createExposedAccessCode: params={}", request);

        final String code = exposedAccessCodeGenerator.generateAccessCode();

        final LocalDateTime currentTime = DateUtils.currentUTCLocalDateTime();
        final LocalDateTime expirationDate = computeExpirationDate(currentTime, request.getExpirationInHours());
        final LocalDate onsetTime = Objects.requireNonNullElse(request.getOnsetDate(), currentTime.toLocalDate());

        ExposedAccessCode exposedAccessCode = ExposedAccessCode.builder()
                .accessCode(code)
                .active(Boolean.TRUE)
                .onsetDate(onsetTime)
                .createdAt(currentTime)
                .expireAt(expirationDate)
                .activatedAt(currentTime)
                .build();
        exposedAccessCode = exposedAccessCodeRepository.save(exposedAccessCode);

        final ExposedAccessCodeSrvDto response = ExposedAccessCodeSrvDtoMapper.INSTANCE.entityToSrvDto(exposedAccessCode);
        log.debug("END - createExposedAccessCode: response={}", response);
        return response;
    }

    @Override
    @Transactional
    public AuthenticationTokenSrvDto getAuthenticationToken(final GetAuthenticationTokenRQSrvDto srvRequest) {
        log.debug("BEGIN - getAuthenticationToken: params={}", srvRequest);

        final ExposedAccessCode exposedAccessCode =
                exposedAccessCodeRepository.findByAccessCodeAndExpireAtGreaterThanAndActiveIsTrue(
                        srvRequest.getAuthorizationCode(),
                        DateUtils.currentUTCLocalDateTime())
                        .orElseThrow(() -> new IBCovidCodeGenException(
                                CodeGenErrorCodes.ACCESS_CODE_NOT_VALID, srvRequest.getAuthorizationCode()));

        final AuthenticationTokenSrvDto response = AuthenticationTokenSrvDto.builder()
                .accessToken(authorizationTokenGenerator.generateToken(exposedAccessCode, srvRequest.getFake()))
                .build();

        exposedAccessCodeRepository.delete(exposedAccessCode);

        log.debug("END - createExposedAccessCode: response={}", response);
        return response;
    }

    private LocalDateTime computeExpirationDate(final LocalDateTime initialDate, final Long duration) {
        return initialDate.plusHours(Objects.requireNonNullElse(duration, appProperties.getExpirationDuration()));
    }

}
