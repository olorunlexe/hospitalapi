package com.assessment.hospitalapi.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {

    private boolean status;
    private String message;
    private List<String> errors;
    private int code;
    private Object data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static GenericResponse generic400BadRequest(String errors) {
        return genericErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }

    public static GenericResponse generic404NotFoundRequest(String errors) {
        return genericErrorResponse(HttpStatus.NOT_FOUND, errors);
    }

    public static GenericResponse generic401UnauthorizedRequest(String errors) {
        return genericErrorResponse(HttpStatus.UNAUTHORIZED, errors);
    }

    public static GenericResponse genericUserNotFoundException() {
        return genericErrorResponse(HttpStatus.NOT_FOUND, "User record not found");
    }

    public static GenericResponse genericValidationErrors(List<String> errors) {
        return genericErrorResponse(HttpStatus.BAD_REQUEST, "Missing required parameter(s)", errors);
    }

    public static GenericResponse genericInternalServerError(String message) {
        message = ((message != null) ? message : "Oops! We're unable to process this request now.");
        return genericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static GenericResponse generic200Response(String message) {
        return generic200Response(message, null);
    }

    public static GenericResponse generic200Response(Object data) {
        return generic200Response(null, data);
    }

    public static GenericResponse generic200Response(String message, Map<String, Object> data) {
        return generic200Response(message, (Object) data);
    }

    public static GenericResponse generic200Response(String message, Object data) {
        GenericResponse response = new GenericResponse();
        response.setStatus(true);
        response.setCode(HttpStatus.OK.value());
        response.setMessage(((message != null && !message.isEmpty()) ? message : "Operation Successful"));
        if(data != null) response.setData(data);
        return response;
    }

    public static Map<String, Object> genericResponse(HttpStatus status, String message, Map<String, Object> data) {
        return genericResponse(status, message, data);
    }

    public static Map<String, Object> genericResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("code", status.value());
        response.put("message", message);
        if(!Objects.isNull(data))
            response.put("data", data);
        return response;
    }

    public static GenericResponse genericErrorResponse(HttpStatus status, String message) {
        return genericErrorResponse(status, message, Collections.singletonList(message));
    }

    public static GenericResponse genericErrorResponse(HttpStatus status, String message, List<String> errors) {
        return genericErrorResponse(status, message, errors, null);
    }

    public static GenericResponse genericErrorResponse(HttpStatus status, String message, Map<String, Object> data) {
        if(StringUtils.isEmpty(message))
            message = status.getReasonPhrase();
        return genericErrorResponse(status, message, Collections.singletonList(message), data);
    }

    public static GenericResponse genericErrorResponse(HttpStatus status, String message, List<String> errors, Object data) {
        GenericResponse response = new GenericResponse();
        response.setStatus(false);
        response.setCode(status.value());
        response.setMessage(message);
        response.setErrors(errors);
        if (!Objects.isNull(data)) response.setData(data);
        return response;
    }

    public static<T> Map<String, Object> buildPaginatedResponse(Page<T> object, Pageable pageable, String key) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("total", object.getTotalElements());
        meta.put("size", pageable.getPageSize());
        meta.put("page", pageable.getPageNumber());

        Map<String, Object> data = new HashMap<>();
        data.put(key, object.getContent());
        data.put("meta", meta);
        return data;
    }

}
