package com.assessment.hospitalapi.dao;

import com.assessment.hospitalapi.entities.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Long> {
    boolean existsByName(String name);
}