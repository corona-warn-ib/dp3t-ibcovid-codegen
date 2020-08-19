package es.caib.dp3t.ibcovid.codegen.controller.errorhandler;

import es.caib.dp3t.ibcovid.codegen.common.exception.CodeGenErrorCodes;
import es.caib.dp3t.ibcovid.codegen.common.exception.IBCovidCodeGenException;
import es.caib.dp3t.ibcovid.codegen.controller.model.ErrorMessageDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class CodegenExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorMessageDto>> handleException(final Exception ex) {
        final IBCovidCodeGenException exception = new IBCovidCodeGenException(
                ex, CodeGenErrorCodes.GENERAL_ERROR, "If it persists, please contact the administrator");
        return processExceptionAndLogError(exception);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleHttpClientErrorException(final HttpClientErrorException ex) {
        IBCovidCodeGenException exception = null;
        if(HttpStatus.FORBIDDEN.value() == (ex.getRawStatusCode())){
            exception = new IBCovidCodeGenException(
                    ex, CodeGenErrorCodes.FORBIDDEN, "You don't have enough permissions.");
        } else if(HttpStatus.BAD_REQUEST.value() == ex.getRawStatusCode()){
            exception = new IBCovidCodeGenException(
                    ex, CodeGenErrorCodes.BAD_REQUEST, "Bad request.");
        } else{
            exception = new IBCovidCodeGenException(
                    ex, CodeGenErrorCodes.GENERAL_ERROR, "If it persists, please contact the administrator.");
        }
        return processExceptionAndLogError(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        final List<ErrorMessageDto> errorMessage = getErrorMessages(ex.getBindingResult());
        return buildResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IBCovidCodeGenException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleIBCovidCodeGenException(final IBCovidCodeGenException ex) {
        return processExceptionAndLogError(ex);
    }

    private ResponseEntity<List<ErrorMessageDto>> processExceptionAndLogError(final IBCovidCodeGenException ex) {
        log.error(ex.getMessage(), ex);
        final ErrorMessageDto errorMessage = getErrorMessage(ex);
        return buildResponse(errorMessage, ex.getError().getHttpStatus());
    }

    private ErrorMessageDto getErrorMessage(final IBCovidCodeGenException ex) {
        return ErrorMessageDto.builder()
                .code(ex.getError().getCode())
                .message(ex.getMessage())
                .build();
    }

    private List<ErrorMessageDto> getErrorMessages(final BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(this::getErrorMessage)
                .collect(Collectors.toList());
    }

    private ErrorMessageDto getErrorMessage(final ObjectError error) {
        final String errorMessage;
        if (error instanceof FieldError) {
            final FieldError fieldError = (FieldError) error;
            errorMessage = String.format("field=[%s], message=[%s]", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            errorMessage = error.getDefaultMessage();
        }

        return ErrorMessageDto.builder()
                .code(CodeGenErrorCodes.VALIDATION_ERROR.getCode())
                .message(errorMessage)
                .build();
    }

    private ResponseEntity<List<ErrorMessageDto>> buildResponse(
            final ErrorMessageDto errorMessage, final HttpStatus httpStatus) {
        return buildResponse(Collections.singletonList(errorMessage), httpStatus);
    }

    private ResponseEntity<List<ErrorMessageDto>> buildResponse(
            final List<ErrorMessageDto> errorMessages, final HttpStatus httpStatus) {
        return new ResponseEntity<>(errorMessages, httpStatus);
    }

}
