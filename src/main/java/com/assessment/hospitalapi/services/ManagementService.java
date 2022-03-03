package com.assessment.hospitalapi.services;

import com.assessment.hospitalapi.dao.PatientRepository;
import com.assessment.hospitalapi.dao.StaffRepository;
import com.assessment.hospitalapi.dto.StaffDTO;
import com.assessment.hospitalapi.entities.Staff;
import com.assessment.hospitalapi.exceptions.CustomApplicationException;
import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.helpers.MapUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;


@Service
public class ManagementService {
    private StaffRepository staffRepository;
    private PatientRepository patientRepository;

    private static final Logger log = getLogger(ManagementService.class.getName());

    public ManagementService(StaffRepository staffRepository, PatientRepository patientRepository) {
        this.staffRepository = staffRepository;
        this.patientRepository = patientRepository;
    }

    public GenericResponse enrollStaff(Map<String, Object> request) {
        String name = MapUtil.getStringValue(request, "name");
        if (staffRepository.existsByName(name)) {
            return GenericResponse.genericErrorResponse(HttpStatus.BAD_REQUEST, "This name is not available. Please choose another one");
        }
        Staff staff = new Staff();
        staff.setName(name);
        staff = staffRepository.save(staff);
        return GenericResponse.generic200Response("Staff created successfully", new StaffDTO(staff));
    }

    public GenericResponse updateStaff(Map<String, Object> request) {
        String uuid = MapUtil.getStringValue(request, "uuid");
        String updatedName = MapUtil.getStringValue(request, "updatedName");
        var staff = staffRepository.findByUuid(uuid);
        if (staff.isEmpty()) {
            return GenericResponse.genericErrorResponse(HttpStatus.BAD_REQUEST, "Staff record does not exist");
        }
        if (staffRepository.existsByName(updatedName)) {
            return GenericResponse.genericErrorResponse(HttpStatus.BAD_REQUEST, "This name is not available. Please choose another one");
        }
        Staff updatedStaff = staff.get();
        updatedStaff.setName(updatedName);
        updatedStaff = staffRepository.save(updatedStaff);
        return GenericResponse.generic200Response("Staff updated successfully", new StaffDTO(updatedStaff));
    }

    public GenericResponse fetchAllPatients() {
        return GenericResponse.generic200Response("Staff updated successfully", patientRepository.findByAgeGreaterThanEqual(2));
    }

    public GenericResponse deleteMultiplePatientsProfile(List<Long> id, String from, String to) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        patientRepository.deleteByIdAndLastVisitDateBetween(id, LocalDateTime.parse(from,df), LocalDateTime.parse(to,df));
        return GenericResponse.genericErrorResponse(HttpStatus.OK, "Records Deleted Successfully");
    }

    public void fetchPatientRecord(String patientName, Writer writer) {
        var patient = patientRepository.findByName(patientName);
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getAge(), patient.getLastVisitDate());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error While writing CSV ", e);
        }
    }
}
