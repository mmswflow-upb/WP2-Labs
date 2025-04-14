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

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "care_provider_id")
    private CareProvider careProvider;



    // One MedicalEncounter can have many HealthService entries.
    @OneToMany(mappedBy = "medicalEncounter", cascade = CascadeType.ALL)
    private List<HealthService> healthServices = new ArrayList<>();
}
