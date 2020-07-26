package es.caib.dp3t.ibcovid.codegen.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class CreateExposedAccessCodeRSDto implements Serializable {
    private static final long serialVersionUID = -8993283431327395432L;

    String accessCode;
    Boolean active;
    Long onsetDate;
    Long expireAt;
    Long activatedAt;

}
