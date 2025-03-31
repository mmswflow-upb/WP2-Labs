package com.wad.firstmvc.repositories;

import com.wad.firstmvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed
}
