package com.assessment.hospitalapi.exceptions.handler;


import com.assessment.hospitalapi.exceptions.CustomApplicationException;
import com.assessment.hospitalapi.helpers.GenericResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.assessment.hospitalapi.helpers.GenericResponse.*;


@RestControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
        logger.debug("global exception handler initialised");
    }

    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<GenericResponse> handleThrowable(CustomApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(genericErrorResponse(e.getHttpStatus(), e.getMessage(), e.getErrors(), e.getData()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse> handleInternalServerError(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericInternalServerError("Something went wrong internally"));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<GenericResponse> handleError(MissingRequestHeaderException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generic400BadRequest(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GenericResponse> handleError(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(genericErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage(), Collections.singletonList(e.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericResponse> handleError(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generic400BadRequest(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse> handleError(HttpMessageNotReadableException e) {
        String errorMessage = e.getMessage();
        String[] split = errorMessage != null ? errorMessage.split(":") : null;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generic400BadRequest((split == null || split.length == 0) ? errorMessage : split[0]));
    }

    private HttpStatus getHttpStatus(HttpServletRequest request) {

        String code = request.getParameter("code");
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        HttpStatus httpStatus;

        if (status != null) httpStatus = HttpStatus.valueOf(status);
        else if (!StringUtils.isEmpty(code)) httpStatus = HttpStatus.valueOf(code);
        else httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return httpStatus;
    }

    private String getErrorMessage(HttpServletRequest request, HttpStatus httpStatus) {
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (message != null && !message.isEmpty()) return message;

        message = switch (httpStatus) {
            case NOT_FOUND -> "The resource does not exist";
            case INTERNAL_SERVER_ERROR -> "Something went wrong internally";
            case FORBIDDEN -> "Permission denied";
            case TOO_MANY_REQUESTS -> "Too many requests";
            default -> httpStatus.getReasonPhrase();
        };

        return message;
    }


}
