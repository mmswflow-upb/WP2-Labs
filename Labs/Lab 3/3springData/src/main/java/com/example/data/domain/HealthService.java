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

    private String description; // e.g. "Flu Shot", "X-Ray", etc.
    private String type;        // e.g. "Vaccination", "Imaging", etc.

    // Each health service belongs to one medical encounter.
    @ManyToOne
    @JoinColumn(name = "medical_encounter_id")
    private MedicalEncounter medicalEncounter;
}
