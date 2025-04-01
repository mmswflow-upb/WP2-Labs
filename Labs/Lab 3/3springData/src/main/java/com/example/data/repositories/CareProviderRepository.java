package com.example.data.repositories;

import com.example.data.domain.Patient;
import com.example.data.domain.CareProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareProviderRepository extends JpaRepository<CareProvider, Long> {

    // (3) Given care provider name, return all distinct patients he/she treated.
    @Query("SELECT DISTINCT me.patient FROM MedicalEncounter me WHERE me.careProvider.name = :providerName")
    List<Patient> findPatientsByCareProviderName(@Param("providerName") String providerName);
}
