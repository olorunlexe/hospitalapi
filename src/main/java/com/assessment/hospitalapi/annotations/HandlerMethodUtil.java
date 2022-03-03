package com.assessment.hospitalapi.annotations;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;

public class HandlerMethodUtil {

    public HandlerMethodUtil() {
    }

    public static boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    public static <A extends Annotation> A getMethodAnnotation(Class<A> annotationClass, HandlerMethod handlerMethod){
        A annotation = handlerMethod.getMethodAnnotation(annotationClass);

        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod().getDeclaringClass(), annotationClass);
        }

        return annotation;
    }

    public static String getHeaderParam(HttpServletRequest request, String headerParamName){
        String headerValue = request.getHeader(headerParamName);

        if(StringUtils.isEmpty(headerValue)) {
            Map<String, String[]> parameterMap = request.getParameterMap();

            if(parameterMap.containsKey(headerParamName)) {
                String[] values = parameterMap.get(headerParamName);

                if(values != null && values.length > 0) {
                    headerValue = values[0];
                }
            }
        }

        return headerValue;
    }

    public static String getIpFromRequest (HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        address = address == null || address.length() == 0 ? request.getHeader("X-FORWARDED-FOR") : address;
        address = address == null || address.length() == 0 ? request.getHeader("x-forwarded-for") : address;
        address = address == null || address.length() == 0 ? request.getRemoteAddr() : address;
        return address;
    }


}
