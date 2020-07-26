package es.caib.dp3t.ibcovid.codegen.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class GetAuthenticationTokenRQDto implements Serializable {
    private static final long serialVersionUID = 1143724223241191774L;

    @NotBlank
    String authorizationCode;

    @Min(0)
    @Max(1)
    int fake;

}
