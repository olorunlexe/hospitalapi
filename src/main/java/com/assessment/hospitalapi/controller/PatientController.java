package com.assessment.hospitalapi.controller;

import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.model.DeletePatientRequest;
import com.assessment.hospitalapi.services.ManagementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("api/v1/patient")
@RestController
public class PatientController {
    private ManagementService managementService;

    public PatientController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", paramType = "header", required = true, dataType = "java.lang.String")
    })
    public ResponseEntity<GenericResponse> fetchPatients() {
        var response = managementService.fetchAllPatients();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/fetch-patient-record")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", paramType = "header", required = true, dataType = "java.lang.String")
    })
    public void fetchPatientRecord(@RequestParam(value = "patientName", required = true) String patientName, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"patient.csv\"");
        managementService.fetchPatientRecord(patientName, servletResponse.getWriter());
    }

    @DeleteMapping("/delete-multiple-patient-record")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", paramType = "header", required = true, dataType = "java.lang.String")
    })
    public GenericResponse deleteMultiplePatientsProfile(@RequestBody DeletePatientRequest request) {
        return managementService.deleteMultiplePatientsProfile(request.getIds(), request.getFrom(), request.getTo());
    }
}
