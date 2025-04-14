package com.example.data.repositories;

import com.example.data.domain.CareProvider;
import com.example.data.domain.MedicalEncounter;
import com.example.data.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicalEncounterRepository extends JpaRepository<MedicalEncounter, Long> {

    @Query("SELECT DISTINCT me.patient FROM MedicalEncounter me WHERE me.date = :date")
    List<Patient> findPatientsByEncounterDate(@Param("date") LocalDate date);

    // Query for care providers who performed at least one health service for a given health issue type
    @Query("SELECT DISTINCT hs.medicalEncounter.careProvider " +
            "FROM HealthService hs " +
            "WHERE hs.healthIssue.type = :issueType")
    List<CareProvider> findCareProvidersByHealthIssueType(@Param("issueType") String issueType);

}


