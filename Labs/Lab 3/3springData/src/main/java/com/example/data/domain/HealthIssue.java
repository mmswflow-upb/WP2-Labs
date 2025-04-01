package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // e.g., "Flu", "Allergy", etc.

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // One HealthIssue can have many HealthService entries.
    @OneToMany(mappedBy = "healthIssue", cascade = CascadeType.ALL)
    private List<HealthService> healthServices = new ArrayList<>();
}
