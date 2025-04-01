package com.example.data.repositories;

import com.example.data.domain.HealthIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthIssueRepository extends JpaRepository<HealthIssue, Long> {

    // (1a) All health issues for a patient.
    @Query("SELECT hi FROM HealthIssue hi WHERE hi.patient.id = :patientId")
    List<HealthIssue> findByPatientId(@Param("patientId") Long patientId);
}
