package com.example.data.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;

    // One CareProvider has many MedicalEncounters.
    @OneToMany(mappedBy = "careProvider", cascade = CascadeType.ALL)
    private List<MedicalEncounter> medicalEncounters = new ArrayList<>();
}
