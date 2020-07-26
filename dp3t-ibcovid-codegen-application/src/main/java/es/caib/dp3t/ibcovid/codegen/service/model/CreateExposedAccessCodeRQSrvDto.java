package es.caib.dp3t.ibcovid.codegen.service.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateExposedAccessCodeRQSrvDto {
    Long expirationInHours;
    LocalDate onsetDate;

}
