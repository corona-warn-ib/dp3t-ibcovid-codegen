package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.data.ExposedAccessCodeRepository;
import es.caib.dp3t.ibcovid.codegen.data.model.ExposedAccessCode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log4j2
class AccessCodeGenerator {
    private static final int EXPOSED_ACCESS_CODE_LENGTH = 12;

    private final ExposedAccessCodeRepository exposedAccessCodeRepository;

    public AccessCodeGenerator(final ExposedAccessCodeRepository exposedAccessCodeRepository) {
        this.exposedAccessCodeRepository = exposedAccessCodeRepository;
    }

    public String generateAccessCode() {
        String accessCode = null;
        Optional<ExposedAccessCode> existingValue = null;
        do {
            accessCode = RandomStringUtils.randomNumeric(EXPOSED_ACCESS_CODE_LENGTH);
            log.trace("accessCode={}", accessCode);

            existingValue = exposedAccessCodeRepository.findByAccessCode(accessCode);

            log.trace("exists in database={}", existingValue.isPresent());
        } while (existingValue.isPresent());

        return accessCode;
    }

}
