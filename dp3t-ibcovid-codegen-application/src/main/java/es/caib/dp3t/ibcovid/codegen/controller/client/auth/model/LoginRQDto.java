package es.caib.dp3t.ibcovid.codegen.controller.client.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class LoginRQDto implements Serializable {
    private static final long serialVersionUID = -8578192413453434938L;

    @NotBlank
    String username;

    @NotBlank
    String password;

}
