package es.caib.dp3t.ibcovid.codegen.service.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class ExposedAccessCodeSrvDto {
    Long id;
    String accessCode;
    Boolean active;
    LocalDate onsetDate;
    LocalDateTime createdAt;
    LocalDateTime expireAt;
    LocalDateTime activatedAt;

}
