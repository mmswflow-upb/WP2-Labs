package com.example.data.controllers;

import com.example.data.domain.CareProvider;
import com.example.data.domain.HealthIssue;
import com.example.data.domain.Patient;
import com.example.data.repositories.MedicalEncounterRepository;
import com.example.data.repositories.PatientRepository;
import com.example.data.repositories.HealthIssueRepository;
import com.example.data.repositories.CareProviderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/queries")
public class QueryController {

    private final HealthIssueRepository healthIssueRepository;
    private final MedicalEncounterRepository medicalEncounterRepository;
    private final PatientRepository patientRepository;
    private final CareProviderRepository careProviderRepository;

    public QueryController(HealthIssueRepository healthIssueRepository,
                           MedicalEncounterRepository medicalEncounterRepository,
                           PatientRepository patientRepository,
                           CareProviderRepository careProviderRepository) {
        this.healthIssueRepository = healthIssueRepository;
        this.medicalEncounterRepository = medicalEncounterRepository;
        this.patientRepository = patientRepository;
        this.careProviderRepository = careProviderRepository;
    }

    @GetMapping
    public String showQueriesPage(Model model) {
        // Optionally initialize model attributes if needed.
        return "queries"; // Thymeleaf will render queries.html
    }

    // 1) Given patient ID -> all health issues AND health issues with at least one service.
    @PostMapping("/patientIssues")
    public String getPatientIssues(@RequestParam Long patientId,
                                   @RequestParam String action,
                                   Model model) {
        List<HealthIssue> issues;
        if ("issuesWithService".equals(action)) {
            issues = healthIssueRepository.findIssuesWithServiceByPatientId(patientId);
        } else {
            issues = healthIssueRepository.findByPatientId(patientId);
        }
        // Convert list to a generic table format (list of rows with columns)
        List<String> columns = Arrays.asList("ID", "Type");
        List<List<String>> rows = new ArrayList<>();
        for (HealthIssue issue : issues) {
            rows.add(Arrays.asList(issue.getId().toString(), issue.getType()));
        }
        model.addAttribute("resultColumns", columns);
        model.addAttribute("results", rows);
        return "queries";
    }

    // 2) Given a date -> all patients who had a medical encounter that date
    @PostMapping("/encounterDate")
    public String getPatientsByEncounterDate(@RequestParam String encounterDate, Model model) {
        LocalDate date = LocalDate.parse(encounterDate);
        List<Patient> patients = medicalEncounterRepository.findPatientsByEncounterDate(date);
        List<String> columns = Arrays.asList("ID", "Name");
        List<List<String>> rows = new ArrayList<>();
        for (Patient p : patients) {
            rows.add(Arrays.asList(p.getId().toString(), p.getName()));
        }
        model.addAttribute("resultColumns", columns);
        model.addAttribute("results", rows);
        return "queries";
    }

    // 3) Given CareProvider name -> all his/her patients
    @PostMapping("/careProviderPatients")
    public String getPatientsByCareProvider(@RequestParam String providerName, Model model) {
        List<Patient> patients = careProviderRepository.findPatientsByCareProviderName(providerName);
        List<String> columns = Arrays.asList("ID", "Name");
        List<List<String>> rows = new ArrayList<>();
        for (Patient p : patients) {
            rows.add(Arrays.asList(p.getId().toString(), p.getName()));
        }
        model.addAttribute("resultColumns", columns);
        model.addAttribute("results", rows);
        return "queries";
    }

    // 4) Given health issue type -> all care providers who performed a health service for that health issue
    @PostMapping("/issueCareProviders")
    public String getCareProvidersByIssue(@RequestParam String issueType, Model model) {
        List<CareProvider> providers = medicalEncounterRepository.findCareProvidersByHealthIssueType(issueType);
        List<String> columns = Arrays.asList("ID", "Name", "Specialty");
        List<List<String>> rows = new ArrayList<>();
        for (CareProvider cp : providers) {
            rows.add(Arrays.asList(cp.getId().toString(), cp.getName(), cp.getSpecialty()));
        }
        model.addAttribute("resultColumns", columns);
        model.addAttribute("results", rows);
        return "queries";
    }
}
