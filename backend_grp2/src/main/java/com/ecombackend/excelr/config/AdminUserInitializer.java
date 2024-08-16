package com.ecombackend.excelr.config;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecombackend.excelr.model.Role;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.repository.RoleRepository;
import com.ecombackend.excelr.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class AdminUserInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        String adminEmail = "admin@gmail.com";
        String adminPassword = "admin";
        String adminRoleName = "ADMIN";
        String adminFirstName = "Ritesh";
        String adminLastName = "Chavhan";
        String adminMobileNumber = "1234567890";
        String adminUsername = "admin";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            // Check if the admin role exists, otherwise create it
            Role adminRole = roleRepository.findByName(adminRoleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(adminRoleName);
                        return roleRepository.save(newRole);
                    });

            // Create the admin user
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRoles(Set.of(adminRole));  // Assign the role
            admin.setFirstName(adminFirstName);
            admin.setLastName(adminLastName);
            admin.setMobileNumber(adminMobileNumber);
            admin.setUsername(adminUsername);

            userRepository.save(admin); 
        }
    }
}
