package com.assessment.hospitalapi.controller;

import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.helpers.MapValidator;
import com.assessment.hospitalapi.services.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.assessment.hospitalapi.helpers.GenericResponse.genericValidationErrors;

@RequestMapping("api/v1/patient")
@RestController
public class PatientController {
    private ManagementService managementService;

    public PatientController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    public ResponseEntity<GenericResponse> fetchPatients() {
        var response = managementService.fetchAllPatients();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/fetch-patient-record")
    public void fetchPatientRecord(@RequestParam(value = "patientName", required = true) String patientName, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"patient.csv\"");
        managementService.fetchPatientRecord(patientName,servletResponse.getWriter());
    }
}
