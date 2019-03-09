package com.expense.exception;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoAuthorizationException.class})
    public ResponseEntity<Object> handleNoAuthorization(RuntimeException exception, WebRequest request) {
        String message = exception.getMessage();
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(RuntimeException exception, WebRequest request) {
        String message = exception.getMessage();
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {MongoWriteException.class})
    public ResponseEntity<Object> handleMongoWriteException(RuntimeException exception, WebRequest request) {
        String message = exception.getCause().getMessage();
        return handleExceptionInternal(exception, message,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
