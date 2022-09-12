package com.example.project.dto;

public class LoginResponseDTO {
    public String token;
    public String message;
    public String userId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String message, String userId) {
        this.token = token;
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
