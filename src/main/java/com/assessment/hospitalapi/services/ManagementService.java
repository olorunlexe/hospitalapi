package com.assessment.hospitalapi.services;

import com.assessment.hospitalapi.dao.PatientRepository;
import com.assessment.hospitalapi.dao.StaffRepository;
import com.assessment.hospitalapi.dto.StaffDTO;
import com.assessment.hospitalapi.entities.Staff;
import com.assessment.hospitalapi.exceptions.CustomApplicationException;
import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.helpers.MapUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ManagementService {
    private StaffRepository staffRepository;
    private PatientRepository patientRepository;

    public ManagementService(StaffRepository staffRepository, PatientRepository patientRepository) {
        this.staffRepository = staffRepository;
        this.patientRepository = patientRepository;
    }

    public GenericResponse enrollStaff(Map<String, Object> request) {
        String name = MapUtil.getStringValue(request, "name");
        if (staffRepository.existsByName(name)) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "This name is not available. Please choose another one");
        }
        Staff staff = new Staff();
        staff.setName(name);
        staff = staffRepository.save(staff);
        return GenericResponse.generic200Response("Staff created successfully", new StaffDTO(staff));
    }
}
