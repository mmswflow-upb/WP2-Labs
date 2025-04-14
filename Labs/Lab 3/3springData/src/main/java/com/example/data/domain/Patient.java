package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One Patient has many MedicalEncounters.
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MedicalEncounter> medicalEncounters = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<HealthIssue> healthIssues = new ArrayList<>();
}
