package es.caib.dp3t.ibcovid.codegen.common.exception;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class IBCovidCodeGenException extends RuntimeException {
    private static final long serialVersionUID = -1193524939205284224L;

    private final CodeGenErrorCodes error;

    public IBCovidCodeGenException(final CodeGenErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params));
        this.error = error;
    }

    public IBCovidCodeGenException(final Throwable cause, final CodeGenErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params), cause);
        this.error = error;
    }

}
