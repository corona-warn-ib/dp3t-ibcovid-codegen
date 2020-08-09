package es.caib.dp3t.ibcovid.codegen.service.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class DownloadedAccessCodeSrvDto {
    Long id;
    String accessCode;
    LocalDateTime activatedAt;
}
