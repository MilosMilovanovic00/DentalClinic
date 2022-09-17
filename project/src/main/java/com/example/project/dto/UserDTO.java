package com.example.project.dto;

import com.example.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String email;
    private String role;
    private String address;
    private String profilePicture;

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.address = user.getAddress().getAddressPrint();
    }

}
