package es.caib.dp3t.ibcovid.codegen.controller.client.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class LoginTokenDto implements Serializable {
    private static final long serialVersionUID = 6992212696182756321L;

    String token;

}
