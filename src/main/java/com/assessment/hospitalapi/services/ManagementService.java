package com.assessment.hospitalapi.services;

import com.assessment.hospitalapi.dao.PatientRepository;
import com.assessment.hospitalapi.dao.StaffRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    private StaffRepository staffRepository;
    private PatientRepository patientRepository;

    public ManagementService(StaffRepository staffRepository, PatientRepository patientRepository) {
        this.staffRepository = staffRepository;
        this.patientRepository = patientRepository;
    }


}
