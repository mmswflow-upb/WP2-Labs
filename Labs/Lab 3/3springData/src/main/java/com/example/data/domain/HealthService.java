package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // e.g., "Flu Shot"
    private String type;        // e.g., "Vaccination"

    // Many HealthServices belong to one HealthIssue.
    @ManyToOne
    @JoinColumn(name = "health_issue_id")
    private HealthIssue healthIssue;

    // Many HealthServices belong to one MedicalEncounter.
    @ManyToOne
    @JoinColumn(name = "medical_encounter_id")
    private MedicalEncounter medicalEncounter;
}
