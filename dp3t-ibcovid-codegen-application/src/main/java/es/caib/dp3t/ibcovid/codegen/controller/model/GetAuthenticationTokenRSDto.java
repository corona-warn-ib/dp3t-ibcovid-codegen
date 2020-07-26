package es.caib.dp3t.ibcovid.codegen.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class GetAuthenticationTokenRSDto implements Serializable {
    private static final long serialVersionUID = -8886500074886635205L;

    String accessToken;

}
