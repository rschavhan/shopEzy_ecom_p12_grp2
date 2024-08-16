package com.ecombackend.excelr.dto;

import java.util.Set;

import com.ecombackend.excelr.model.Role;

public class LoginResponse {
	private String userName;
    private Long userId;
    private Set<Role> roles;

	public LoginResponse(String userName, Long userId, Set<Role> roles) {
	super();
	this.userName = userName;
	this.userId = userId;
	this.roles = roles;
}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	// Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
