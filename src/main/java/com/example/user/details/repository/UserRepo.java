package com.example.user.details.repository;

import com.example.user.details.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Long> {
    boolean existsByEmail(String email);
}
