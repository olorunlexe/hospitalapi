package com.assessment.hospitalapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateStaffRequest {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
