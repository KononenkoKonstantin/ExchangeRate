package org.example.exchangerate.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> errorsBody = new LinkedHashMap<>();
        errorsBody.put("timestamp", LocalDateTime.now());
        errorsBody.put("status", HttpStatus.BAD_REQUEST);
        final List<String> messagesList = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        errorsBody.put("errors", messagesList);
        return new ResponseEntity<>(errorsBody, headers, status);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<Object> handleCustomException(InvalidDateFormatException ex,
                                                        WebRequest request) {
        return getObjectResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RemoteServiceNotAvailableException.class)
    public ResponseEntity<Object> handleCustomException(RemoteServiceNotAvailableException ex,
                                                        WebRequest request) {
        return getObjectResponseEntity(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError fieldError) {
            String field = fieldError.getField();
            String message = e.getDefaultMessage();
            return "%s %s".formatted(field, message);
        }
        return e.getDefaultMessage();
    }

    private ResponseEntity<Object> getObjectResponseEntity(String message,
                                                           HttpStatus httpStatus) {
        Map<String, Object> errorsBody = new LinkedHashMap<>();
        errorsBody.put("timestamp", LocalDateTime.now());
        errorsBody.put("status", httpStatus);
        errorsBody.put("errors", message);
        return new ResponseEntity<>(errorsBody, httpStatus);
    }
}
