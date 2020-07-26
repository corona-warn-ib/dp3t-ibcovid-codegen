package es.caib.dp3t.ibcovid.codegen.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
@Builder
public class ErrorMessageDto implements Serializable {
    private static final long serialVersionUID = 7608345655100088969L;

    String code;
    String message;

}
