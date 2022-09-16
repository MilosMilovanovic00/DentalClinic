package com.example.project.address.user;

import com.example.project.address.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Setter(value = AccessLevel.NONE)
    private Long id;
    private String name;
    private String surname;
    private String personalIdentificationNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String profileImage;
    private Role role;
    @OneToOne
    @JoinColumn
    private Address address;
    public String getFullName(){
        return getName()+" "+getSurname();
    }
}
