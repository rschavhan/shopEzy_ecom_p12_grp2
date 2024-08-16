package com.ecombackend.excelr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecombackend.excelr.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findById(Long id); 
    
    Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
}
