package com.assessment.hospitalapi.filter;


import com.assessment.hospitalapi.dao.StaffRepository;
import com.assessment.hospitalapi.helpers.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class InboundLoggingFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;
    private StaffRepository staffRepository;

    public InboundLoggingFilter(ObjectMapper objectMapper, StaffRepository staffRepository) {
        this.objectMapper = objectMapper;
        this.staffRepository = staffRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        //add urls to ignore log for
        if (path.startsWith("/api/v1/health")||path.startsWith("/v2/api-docs")) {
            filterChain.doFilter(request, new ResponseWrapper(response));
            return;
        }
        if (path.startsWith("/api/v1/staff") && request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())) {
            filterChain.doFilter(request, new ResponseWrapper(response));
            return;
        }

        String uuid = request.getHeader("uuid");

        if (StringUtils.isEmpty(uuid)) {
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            objectMapper.writeValue(response.getWriter(), GenericResponse.genericErrorResponse(HttpStatus.UNAUTHORIZED, "Requesting Staff UUID is required"));
            return;
        }

        boolean exists = staffRepository.existsByUuid(uuid);
        if (!exists) {
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            objectMapper.writeValue(response.getWriter(), GenericResponse.genericErrorResponse(HttpStatus.UNAUTHORIZED, "Requesting Staff UUID is required"));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
