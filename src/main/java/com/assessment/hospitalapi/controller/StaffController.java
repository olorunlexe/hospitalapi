package com.assessment.hospitalapi.controller;

import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.helpers.MapValidator;
import com.assessment.hospitalapi.model.CreateStaffRequest;
import com.assessment.hospitalapi.model.UpdateStaffRequest;
import com.assessment.hospitalapi.services.ManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.assessment.hospitalapi.helpers.GenericResponse.genericValidationErrors;

@RestController
public class StaffController {
    private ManagementService managementService;
    private ObjectMapper objectMapper;

    public StaffController(ManagementService managementService, ObjectMapper objectMapper) {
        this.managementService = managementService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createStaff(
            @RequestBody CreateStaffRequest request) {
        Map<String, Object> map = objectMapper.convertValue(request, Map.class);
        Map<String, String> rules = new HashMap<>();
        rules.put("name", "required");

        List<String> errors = MapValidator.validate(map, rules);
        if (!errors.isEmpty()) return ResponseEntity.badRequest().body(genericValidationErrors(errors));

        var response = managementService.enrollStaff(map);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<GenericResponse> updateStaff(
            @RequestBody UpdateStaffRequest request) {
        Map<String, Object> map = objectMapper.convertValue(request, Map.class);
        Map<String, String> rules = new HashMap<>();
        rules.put("updatedName", "required");
        rules.put("uuid", "required");

        List<String> errors = MapValidator.validate(map, rules);
        if (!errors.isEmpty()) return ResponseEntity.badRequest().body(genericValidationErrors(errors));

        var response = managementService.updateStaff(map);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
