package com.assessment.hospitalapi.filter;

import com.assessment.hospitalapi.dao.StaffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class BaseFilter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StaffRepository staffRepository;

    @Bean
    public Filter loggingFilter() {
        return new InboundLoggingFilter(objectMapper,staffRepository);
    }
}