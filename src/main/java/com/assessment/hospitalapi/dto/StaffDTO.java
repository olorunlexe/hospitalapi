package com.assessment.hospitalapi.dto;

import com.assessment.hospitalapi.config.CustomLocalDateTimeSerializer;
import com.assessment.hospitalapi.entities.Staff;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class StaffDTO {
    private String name;
    private String uuid;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime registrationDate;

    public StaffDTO(Staff staff){
        name= staff.getName();
        uuid= staff.getUuid();
        registrationDate=staff.getRegistrationDate();
    }
    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
