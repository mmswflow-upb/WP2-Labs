package com.example.data.repositories;

import com.example.data.domain.HealthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthServiceRepository extends JpaRepository<HealthService, Long> {
}
