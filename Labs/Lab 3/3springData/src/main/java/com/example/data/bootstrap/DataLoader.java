package com.example.data.bootstrap;

import com.example.data.domain.*;
import com.example.data.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final HealthIssueRepository healthIssueRepository;
    private final MedicalEncounterRepository medicalEncounterRepository;
    private final CareProviderRepository careProviderRepository;

    public DataLoader(PatientRepository patientRepository,
                      HealthIssueRepository healthIssueRepository,
                      MedicalEncounterRepository medicalEncounterRepository,
                      CareProviderRepository careProviderRepository) {
        this.patientRepository = patientRepository;
        this.healthIssueRepository = healthIssueRepository;
        this.medicalEncounterRepository = medicalEncounterRepository;
        this.careProviderRepository = careProviderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create Patients.
        Patient john = new Patient();
        john.setName("John Doe");
        john = patientRepository.save(john);

        Patient jane = new Patient();
        jane.setName("Jane Smith");
        jane = patientRepository.save(jane);

        // Create HealthIssues.
        HealthIssue flu = new HealthIssue();
        flu.setType("Flu");
        flu.setPatient(john);  // unidirectional relationship: only on HealthIssue side.
        flu = healthIssueRepository.save(flu);

        HealthIssue allergy = new HealthIssue();
        allergy.setType("Allergy");
        allergy.setPatient(jane);
        allergy = healthIssueRepository.save(allergy);

        // Create CareProviders.
        CareProvider drBrown = new CareProvider();
        drBrown.setName("Dr. Brown");
        drBrown.setSpecialty("General Medicine");
        drBrown = careProviderRepository.save(drBrown);

        CareProvider drGreen = new CareProvider();
        drGreen.setName("Dr. Green");
        drGreen.setSpecialty("Allergology");
        drGreen = careProviderRepository.save(drGreen);

        // Create MedicalEncounters.
        MedicalEncounter encounter1 = new MedicalEncounter();
        encounter1.setDate(LocalDate.of(2025, 1, 10));
        encounter1.setPatient(john);
        encounter1.setCareProvider(drBrown);
        encounter1.setHealthIssue(flu);

        MedicalEncounter encounter2 = new MedicalEncounter();
        encounter2.setDate(LocalDate.of(2025, 1, 10));
        encounter2.setPatient(jane);
        encounter2.setCareProvider(drGreen);
        encounter2.setHealthIssue(allergy);

        MedicalEncounter encounter3 = new MedicalEncounter();
        encounter3.setDate(LocalDate.of(2025, 1, 11));
        encounter3.setPatient(john);
        encounter3.setCareProvider(drGreen);
        encounter3.setHealthIssue(flu);

        // Create HealthServices and associate them with encounters.
        HealthService service1 = new HealthService();
        service1.setDescription("Influenza Vaccination");
        service1.setType("Vaccination");
        service1.setMedicalEncounter(encounter1);
        encounter1.getHealthServices().add(service1);

        HealthService service2 = new HealthService();
        service2.setDescription("Allergy Test");
        service2.setType("Diagnostics");
        service2.setMedicalEncounter(encounter2);
        encounter2.getHealthServices().add(service2);

        // (Encounter3 deliberately has no service to test queries.)

        // Save encounters (which cascades to health services if configured).
        medicalEncounterRepository.saveAll(List.of(encounter1, encounter2, encounter3));

        // Update bidirectional relationships.
        john.getMedicalEncounters().add(encounter1);
        john.getMedicalEncounters().add(encounter3);
        jane.getMedicalEncounters().add(encounter2);

        patientRepository.saveAll(List.of(john, jane));

        drBrown.getMedicalEncounters().add(encounter1);
        drGreen.getMedicalEncounters().add(encounter2);
        drGreen.getMedicalEncounters().add(encounter3);

        careProviderRepository.saveAll(List.of(drBrown, drGreen));

        System.out.println("Data loaded successfully!");
    }
}
