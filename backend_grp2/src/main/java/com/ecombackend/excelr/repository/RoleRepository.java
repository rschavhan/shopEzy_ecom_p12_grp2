package com.ecombackend.excelr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecombackend.excelr.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
//	Optional<Role> findByName(String name);

	Optional<Role> findByName(String adminRoleName);
}
