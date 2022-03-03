package com.assessment.hospitalapi.dao;

import com.assessment.hospitalapi.entities.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Long> {
    boolean existsByName(String name);
    boolean existsByUuid(String uuid);
    Optional<Staff> findByUuid(String uuid);
}