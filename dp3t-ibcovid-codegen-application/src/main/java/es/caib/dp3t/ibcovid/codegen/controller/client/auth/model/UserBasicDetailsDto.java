package es.caib.dp3t.ibcovid.codegen.controller.client.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@AllArgsConstructor
@Builder
public class UserBasicDetailsDto implements Serializable {
    private static final long serialVersionUID = -8984451761459634810L;

    String username;
    List<String> roles;

}
