package es.caib.dp3t.ibcovid.codegen.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class CreateExposedAccessCodeRQDto implements Serializable {
    private static final long serialVersionUID = 898336050674901902L;

    @Positive
    Long expirationInHours;

    @Positive
    Long onsetDate;

}
