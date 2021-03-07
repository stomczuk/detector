package com.example.detector.exception;

import com.example.detector.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.FileNotFoundException;
import java.util.Date;

@RestControllerAdvice
public class ExceptionHandling {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> illegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<HttpResponse> illegalArgumentException(FileNotFoundException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BlankNameOrVariantParamException.class)
    public ResponseEntity<HttpResponse> blankNameOrVariantParamException(BlankNameOrVariantParamException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BlankGenderParamException.class)
    public ResponseEntity<HttpResponse> blankGenderParamException(BlankGenderParamException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidGenderParamException.class)
    public ResponseEntity<HttpResponse> invalidGenderParamException(InvalidGenderParamException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidNameOrVariantParamException.class)
    public ResponseEntity<HttpResponse> invalidNameOrVariantParamException(InvalidNameOrVariantParamException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    @ExceptionHandler(InvalidCharacterException.class)
    public ResponseEntity<HttpResponse> invalidCharacterException(InvalidCharacterException e) {
        LOGGER.error(e.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(new Date(), httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity(httpResponse, httpStatus);
    }
}
