package com.example.project.dto;

import com.example.project.user.User;

import java.time.format.DateTimeFormatter;

public class UserDTO {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String email;
    private String role;
    private String address;

    public UserDTO() {
    }

    public UserDTO(String name, String surname, String dateOfBirth, String email, String role, String address) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.role = role;
        this.address = address;
    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.address = user.getAddress().getAddressPrint();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
