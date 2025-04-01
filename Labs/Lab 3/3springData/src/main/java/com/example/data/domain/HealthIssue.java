package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For example, "Flu", "Allergy", etc.
    private String type;

    // Unidirectional many-to-one: each HealthIssue is associated with one Patient.
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private com.example.data.domain.Patient patient;
}
