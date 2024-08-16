package com.ecombackend.excelr.dto;
public class UserRoleUpdateRequest {
    private Long userId;
    private Long newRoleId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNewRoleId() {
        return newRoleId;
    }

    public void setNewRoleId(Long newRoleId) {
        this.newRoleId = newRoleId;
    }
}
