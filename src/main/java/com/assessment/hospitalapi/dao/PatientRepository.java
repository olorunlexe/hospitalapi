package com.assessment.hospitalapi.dao;

import com.assessment.hospitalapi.entities.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByAgeGreaterThanEqual(int age);
    Patient findByName(String name);
}