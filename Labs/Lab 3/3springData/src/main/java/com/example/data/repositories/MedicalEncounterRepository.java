package com.example.data.repositories;

import com.example.data.domain.MedicalEncounter;
import com.example.data.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicalEncounterRepository extends JpaRepository<MedicalEncounter, Long> {

    // (1b) Retrieve distinct health issues (from encounters) for a patient having at least one service.
    @Query("SELECT DISTINCT me.healthIssue FROM MedicalEncounter me " +
            "WHERE me.patient.id = :patientId AND size(me.healthServices) > 0")
    List<?> findHealthIssuesWithServicesByPatientId(@Param("patientId") Long patientId);

    // (2) Given a date, find all patients who had an encounter that day.
    @Query("SELECT DISTINCT me.patient FROM MedicalEncounter me WHERE me.date = :date")
    List<Patient> findPatientsByEncounterDate(@Param("date") LocalDate date);

    // (4) Given a health issue type, find all care providers who performed a service for that health issue.
    // This query looks for encounters that address a specific health issue type and have at least one service.
    @Query("SELECT DISTINCT me.careProvider FROM MedicalEncounter me " +
            "WHERE me.healthIssue.type = :issueType AND size(me.healthServices) > 0")
    List<?> findCareProvidersByHealthIssueType(@Param("issueType") String issueType);
}
