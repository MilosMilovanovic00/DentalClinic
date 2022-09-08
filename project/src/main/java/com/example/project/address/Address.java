package com.example.project.address;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADDRESSES")
public class Address {
    @Id
    @SequenceGenerator(
            sequenceName = "address_sequence",
            name = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    @Setter(AccessLevel.NONE)
    private Long id;
    private String city;
    private String state;
    private String streetName;
    private String number;
}
