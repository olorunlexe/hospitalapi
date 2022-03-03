package com.assessment.hospitalapi.annotations;

import com.assessment.hospitalapi.dao.StaffRepository;
import com.assessment.hospitalapi.exceptions.CustomApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
@ControllerAdvice
public class StaffAccessValidator implements RequestBodyAdvice {

    private StaffRepository staffRepository;

    public StaffAccessValidator(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        List<Annotation> annotations = Arrays.asList(methodParameter.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(AccessValidator.class));
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {

        AccessValidator uuidValidator = methodParameter.getMethodAnnotation(AccessValidator.class);
        HttpHeaders headers = inputMessage.getHeaders();

        String uuid = headers.getFirst("uuid");


        if (uuidValidator.isOptional()) {
            return inputMessage;
        }

        if (StringUtils.isEmpty(uuid)) {
            throw new CustomApplicationException("Requesting Staff UUID is required");
        }

        boolean exists = staffRepository.existsByUuid(uuid);
        if (!exists) {
            throw new CustomApplicationException("Invalid Requesting Staff UUID");
        }

        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
