package com.example.data.controllers;

import com.example.data.domain.*;
import com.example.data.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final PatientRepository patientRepository;
    private final MedicalEncounterRepository encounterRepository;
    private final CareProviderRepository careProviderRepository;
    private final HealthIssueRepository healthIssueRepository;
    private final HealthServiceRepository healthServiceRepository;

    public WebController(PatientRepository patientRepository,
                         MedicalEncounterRepository encounterRepository,
                         CareProviderRepository careProviderRepository,
                         HealthIssueRepository healthIssueRepository,
                         HealthServiceRepository healthServiceRepository) {
        this.patientRepository = patientRepository;
        this.encounterRepository = encounterRepository;
        this.careProviderRepository = careProviderRepository;
        this.healthIssueRepository = healthIssueRepository;
        this.healthServiceRepository = healthServiceRepository;
    }

    // ---------- Patients ----------
    @GetMapping("/patients")
    public String getPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients";
    }

    // ---------- Medical Encounters ----------
    @GetMapping("/encounters")
    public String getEncounters(Model model) {
        model.addAttribute("encounters", encounterRepository.findAll());
        return "encounters";
    }

    // ---------- Care Providers ----------
    @GetMapping("/careproviders")
    public String getCareProviders(Model model) {
        model.addAttribute("careProviders", careProviderRepository.findAll());
        return "careproviders";
    }

    // ---------- Health Issues ----------
    @GetMapping("/healthissues")
    public String getHealthIssues(Model model) {
        model.addAttribute("healthIssues", healthIssueRepository.findAll());
        return "healthissues";
    }

    // ---------- Health Services ----------
    @GetMapping("/healthservices")
    public String getHealthServices(Model model) {
        model.addAttribute("healthServices", healthServiceRepository.findAll());
        return "healthservices";
    }
}
