package com.assessment.hospitalapi.dao;

import com.assessment.hospitalapi.entities.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByAgeGreaterThanEqual(int age);

    Patient findByName(String name);

    @Modifying
    @Transactional
    @Query("delete from Patient p where p.id in(:integers) and p.lastVisitDate >= :from and p.lastVisitDate<= :to")
    void deleteByIdAndLastVisitDateBetween(@Param("integers") List<Long> integers, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}