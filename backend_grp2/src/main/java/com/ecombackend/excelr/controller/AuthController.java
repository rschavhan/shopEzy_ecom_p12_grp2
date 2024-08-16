package com.ecombackend.excelr.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.LoginRequest;
import com.ecombackend.excelr.dto.LoginResponse;
import com.ecombackend.excelr.model.Role;
import com.ecombackend.excelr.model.User;
import com.ecombackend.excelr.service.RoleService;
import com.ecombackend.excelr.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Fetch or create the "ROLE_USER" role
        Role userRole = roleService.findByName("USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("USER");
            return roleService.saveRole(newRole);
        });

        signUpRequest.setRoles(Set.of(userRole));  // Assign the role to the user

        userService.saveUser(signUpRequest);

        return ResponseEntity.ok("User registered successfully with email: " + signUpRequest.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());


        if (userOptional.isEmpty()) {
            System.out.println("User not found: " + loginRequest.getEmail());
        } else {
            System.out.println("User found: " + userOptional.get());
            System.out.println("Password matches: " + passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword()));
        }

        
        
        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
        	
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        User user = userOptional.get();
        System.out.println("AuthController.logInUser()"
        		+user);
        Set<Role> roles = user.getRoles();  // Get the user's roles
        
        LoginResponse loginResponse = new LoginResponse(user.getUsername(),user.getId(),roles);
        System.out.println("AuthController.logInUser()"+loginResponse);
        return ResponseEntity.ok(loginResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
        return ResponseEntity.ok("Logged out successfully");
    }
}
