package es.caib.dp3t.ibcovid.codegen.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CodeGenErrorCodes {
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error has occurred: {0}"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error: {0}"),
    ACCESS_CODE_NOT_VALID(HttpStatus.FORBIDDEN, "Access code not valid: {0}"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "{0}"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "{0}");


    private final HttpStatus httpStatus;
    private final String message;

    public String getCode() {
        return name();
    }

}
