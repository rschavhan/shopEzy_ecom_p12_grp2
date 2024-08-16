package com.ecombackend.excelr.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;

    // Getters and Setters
}
