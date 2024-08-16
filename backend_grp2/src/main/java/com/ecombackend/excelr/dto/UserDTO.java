package com.ecombackend.excelr.dto;

import com.ecombackend.excelr.model.User;

import lombok.Data;

@Data
public class UserDTO {
	 private String username;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String mobileNumber;
	    
	 // Default constructor
	    public UserDTO() {}

	    // Constructor to create UserDTO from User
	    public UserDTO(User user) {
	        this.username = user.getUsername();
	        this.firstName = user.getFirstName();
	        this.lastName = user.getLastName();
	        this.email = user.getEmail();
	        this.mobileNumber = user.getMobileNumber();
	    }


}
