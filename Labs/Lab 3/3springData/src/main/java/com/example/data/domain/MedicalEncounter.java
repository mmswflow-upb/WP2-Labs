package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalEncounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    // Each encounter is for one patient.
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Each encounter is handled by one care provider.
    @ManyToOne
    @JoinColumn(name = "care_provider_id")
    private CareProvider careProvider;

    // Each encounter addresses one health issue.
    @ManyToOne
    @JoinColumn(name = "health_issue_id")
    private HealthIssue healthIssue;

    // One encounter can involve many health services.
    @OneToMany(mappedBy = "medicalEncounter", cascade = CascadeType.ALL)
    private List<HealthService> healthServices = new ArrayList<>();
}
