package com.ecombackend.excelr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.UserDTO;
import com.ecombackend.excelr.service.UserService;

@RestController
@RequestMapping("/api/user")
public class ProfileController {
	
    @Autowired
    private UserService userService;
	
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDTO);
    }
    
    @PutMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserProfile(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
