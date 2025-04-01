package com.example.data.controllers;

import com.example.data.domain.*;
import com.example.data.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalController {

    private final PatientRepository patientRepository;
    private final HealthIssueRepository healthIssueRepository;
    private final MedicalEncounterRepository medicalEncounterRepository;
    private final CareProviderRepository careProviderRepository;
    private final HealthServiceRepository healthServiceRepository;

    public HospitalController(PatientRepository patientRepository,
                              HealthIssueRepository healthIssueRepository,
                              MedicalEncounterRepository medicalEncounterRepository,
                              CareProviderRepository careProviderRepository,
                              HealthServiceRepository healthServiceRepository) {
        this.patientRepository = patientRepository;
        this.healthIssueRepository = healthIssueRepository;
        this.medicalEncounterRepository = medicalEncounterRepository;
        this.careProviderRepository = careProviderRepository;
        this.healthServiceRepository = healthServiceRepository;
    }

    // ---------- CRUD for Patient ----------
    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/patients/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    // ---------- CRUD for HealthIssue ----------
    @PostMapping("/healthissues")
    public HealthIssue createHealthIssue(@RequestBody HealthIssue healthIssue) {
        return healthIssueRepository.save(healthIssue);
    }

    @GetMapping("/healthissues")
    public List<HealthIssue> getAllHealthIssues() {
        return healthIssueRepository.findAll();
    }

    @GetMapping("/healthissues/{id}")
    public HealthIssue getHealthIssue(@PathVariable Long id) {
        return healthIssueRepository.findById(id).orElse(null);
    }

    // ---------- CRUD for MedicalEncounter ----------
    @PostMapping("/encounters")
    public MedicalEncounter createEncounter(@RequestBody MedicalEncounter encounter) {
        return medicalEncounterRepository.save(encounter);
    }

    @GetMapping("/encounters")
    public List<MedicalEncounter> getAllEncounters() {
        return medicalEncounterRepository.findAll();
    }

    @GetMapping("/encounters/{id}")
    public MedicalEncounter getEncounter(@PathVariable Long id) {
        return medicalEncounterRepository.findById(id).orElse(null);
    }

    // ---------- CRUD for HealthService ----------
    @PostMapping("/healthservices")
    public HealthService createHealthService(@RequestBody HealthService service) {
        return healthServiceRepository.save(service);
    }

    @GetMapping("/healthservices")
    public List<HealthService> getAllHealthServices() {
        return healthServiceRepository.findAll();
    }

    @GetMapping("/healthservices/{id}")
    public HealthService getHealthService(@PathVariable Long id) {
        return healthServiceRepository.findById(id).orElse(null);
    }

    // ---------- CRUD for CareProvider ----------
    @PostMapping("/careproviders")
    public CareProvider createCareProvider(@RequestBody CareProvider provider) {
        return careProviderRepository.save(provider);
    }

    @GetMapping("/careproviders")
    public List<CareProvider> getAllCareProviders() {
        return careProviderRepository.findAll();
    }

    @GetMapping("/careproviders/{id}")
    public CareProvider getCareProvider(@PathVariable Long id) {
        return careProviderRepository.findById(id).orElse(null);
    }

    // ---------- Custom Query Endpoints ----------

    // (1a) For a given patient ID, get all health issues.
    @GetMapping("/patients/{patientId}/healthissues")
    public List<HealthIssue> getHealthIssuesByPatient(@PathVariable Long patientId) {
        return healthIssueRepository.findByPatientId(patientId);
    }

    // (1b) For a given patient ID, get all health issues addressed in encounters with at least one service.
    @GetMapping("/patients/{patientId}/healthissues-with-service")
    public List<?> getHealthIssuesWithServiceByPatient(@PathVariable Long patientId) {
        return medicalEncounterRepository.findHealthIssuesWithServicesByPatientId(patientId);
    }

    // (2) For a given date, get all patients who had an encounter that day.
    @GetMapping("/encounters/date/{date}/patients")
    public List<Patient> getPatientsByEncounterDate(@PathVariable String date) {
        return medicalEncounterRepository.findPatientsByEncounterDate(LocalDate.parse(date));
    }

    // (3) For a given CareProvider name, get all his/her patients.
    @GetMapping("/careproviders/{providerName}/patients")
    public List<Patient> getPatientsByCareProvider(@PathVariable String providerName) {
        return careProviderRepository.findPatientsByCareProviderName(providerName);
    }

    // (4) For a given health issue type, get all care providers who performed a service.
    @GetMapping("/healthissues/{issueType}/careproviders")
    public List<?> getCareProvidersByHealthIssueType(@PathVariable String issueType) {
        return medicalEncounterRepository.findCareProvidersByHealthIssueType(issueType);
    }
}
