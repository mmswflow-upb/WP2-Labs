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

        Patient bob = new Patient();
        bob.setName("Bob White");
        bob = patientRepository.save(bob);

        Patient alice = new Patient();
        alice.setName("Alice Johnson");
        alice = patientRepository.save(alice);

        // Create HealthIssues.
        HealthIssue flu = new HealthIssue();
        flu.setType("Flu");
        flu.setPatient(john);  // unidirectional: only on HealthIssue side.
        flu = healthIssueRepository.save(flu);

        HealthIssue backPain = new HealthIssue();
        backPain.setType("Back Pain");
        backPain.setPatient(john);
        backPain = healthIssueRepository.save(backPain);

        HealthIssue allergy = new HealthIssue();
        allergy.setType("Allergy");
        allergy.setPatient(jane);
        allergy = healthIssueRepository.save(allergy);

        HealthIssue cold = new HealthIssue();
        cold.setType("Common Cold");
        cold.setPatient(bob);
        cold = healthIssueRepository.save(cold);

        HealthIssue migraine = new HealthIssue();
        migraine.setType("Migraine");
        migraine.setPatient(alice);
        migraine = healthIssueRepository.save(migraine);

        // Create CareProviders.
        CareProvider drBrown = new CareProvider();
        drBrown.setName("Dr. Brown");
        drBrown.setSpecialty("General Medicine");
        drBrown = careProviderRepository.save(drBrown);

        CareProvider drGreen = new CareProvider();
        drGreen.setName("Dr. Green");
        drGreen.setSpecialty("Allergology");
        drGreen = careProviderRepository.save(drGreen);

        CareProvider drBlack = new CareProvider();
        drBlack.setName("Dr. Black");
        drBlack.setSpecialty("Neurology");
        drBlack = careProviderRepository.save(drBlack);

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

        // Additional Encounters.
        MedicalEncounter encounter4 = new MedicalEncounter();
        encounter4.setDate(LocalDate.of(2025, 1, 12));
        encounter4.setPatient(bob);
        encounter4.setCareProvider(drBrown);
        encounter4.setHealthIssue(cold);

        MedicalEncounter encounter5 = new MedicalEncounter();
        encounter5.setDate(LocalDate.of(2025, 1, 13));
        encounter5.setPatient(alice);
        encounter5.setCareProvider(drBlack);
        encounter5.setHealthIssue(migraine);

        // Create HealthServices and associate them with both encounters and health issues.
        HealthService service1 = new HealthService();
        service1.setDescription("Influenza Vaccination");
        service1.setType("Vaccination");
        service1.setMedicalEncounter(encounter1);
        service1.setHealthIssue(flu);
        encounter1.getHealthServices().add(service1);
        flu.getHealthServices().add(service1);

        HealthService service2 = new HealthService();
        service2.setDescription("Allergy Test");
        service2.setType("Diagnostics");
        service2.setMedicalEncounter(encounter2);
        service2.setHealthIssue(allergy);
        encounter2.getHealthServices().add(service2);
        allergy.getHealthServices().add(service2);

        // (Encounter3 deliberately has no service to test queries.)
        // Additional HealthServices for extra encounters:
        HealthService service3 = new HealthService();
        service3.setDescription("Cold Treatment");
        service3.setType("Medication");
        service3.setMedicalEncounter(encounter4);
        service3.setHealthIssue(cold);
        encounter4.getHealthServices().add(service3);
        cold.getHealthServices().add(service3);

        HealthService service4 = new HealthService();
        service4.setDescription("Migraine Relief");
        service4.setType("Medication");
        service4.setMedicalEncounter(encounter5);
        service4.setHealthIssue(migraine);
        encounter5.getHealthServices().add(service4);
        migraine.getHealthServices().add(service4);

        // Save encounters (which will cascade to health services if cascade is configured).
        medicalEncounterRepository.saveAll(List.of(encounter1, encounter2, encounter3, encounter4, encounter5));

        // Update bidirectional relationships for patients.
        john.getMedicalEncounters().add(encounter1);
        john.getMedicalEncounters().add(encounter3);
        jane.getMedicalEncounters().add(encounter2);
        bob.getMedicalEncounters().add(encounter4);
        alice.getMedicalEncounters().add(encounter5);
        patientRepository.saveAll(List.of(john, jane, bob, alice));

        // Update bidirectional relationships for care providers.
        drBrown.getMedicalEncounters().add(encounter1);
        drBrown.getMedicalEncounters().add(encounter4);
        drGreen.getMedicalEncounters().add(encounter2);
        drGreen.getMedicalEncounters().add(encounter3);
        drBlack.getMedicalEncounters().add(encounter5);
        careProviderRepository.saveAll(List.of(drBrown, drGreen, drBlack));


        System.out.println("Data loaded successfully!");
    }
}
