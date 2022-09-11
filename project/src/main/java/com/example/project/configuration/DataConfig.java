package com.example.project.configuration;

import com.example.project.address.AddressRepository;
import com.example.project.appointment.AppointmentsRepository;
import com.example.project.address.Address;
import com.example.project.user.Role;
import com.example.project.user.User;
import com.example.project.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class DataConfig {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AppointmentsRepository appointmentsRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, AppointmentsRepository appointmentsRepository, AddressRepository addressRepository) {
        return args -> {
            this.addressRepository = addressRepository;
            this.appointmentsRepository = appointmentsRepository;
            this.userRepository = userRepository;
            Address address1 = new Address(
                    1L,
                    "El Cajon",
                    "California",
                    "Poplar Avenue",
                    "1893"
            );
            Address address2 = new Address(
                    2L,
                    "Cordele",
                    "Georgia",
                    "Private Lane",
                    "3724"
            );
            Address address3 = new Address(
                    3L,
                    "Garden City",
                    "New York",
                    "Scott Street",
                    "4173"
            );
            Address address4 = new Address(
                    4L,
                    "Dunnigan",
                    "California",
                    "Highland View Drive",
                    "4062"
            );
            Address address5 = new Address(
                    5L,
                    "Dunnigan",
                    "California",
                    "Eagles Nest Drive",
                    "4101"
            );
            addressRepository.saveAll(List.of(address1, address2, address3, address4, address5));
            User mika = new User(
                    1L,
                    "Mika",
                    "Mikić",
                    "2603198431542",
                    LocalDate.of(1984, MARCH, 26),
                    "mikamikic@gmail.com",
                    "",
                    Role.Patient,
                    address1
            );
            User zika = new User(
                    2L,
                    "žika",
                    "Žikić",
                    "0404194718645",
                    LocalDate.of(1947, APRIL, 4),
                    "zikazikic@gmail.com",
                    "",
                    Role.Patient,
                    address2
            );
            User jasmina = new User(
                    3L,
                    "Jasnina",
                    "Novaković",
                    "1512199718665",
                    LocalDate.of(1997, DECEMBER, 15),
                    "jasminanovakovic@gmail.com",
                    "",
                    Role.Patient,
                    address3
                    );
            User marta = new User(
                    4L,
                    "Marta",
                    "Simić",
                    "2902200401621",
                    LocalDate.of(2004, FEBRUARY, 29),
                    "martasimic@gmail.com",
                    "",
                    Role.Patient,
                    address4
            );
            User dragan = new User(
                    5L,
                    "Dragan",
                    "Marković",
                    "0905197411698",
                    LocalDate.of(1974, JUNE, 9),
                    "draganmarkovic@gmail.com",
                    "",
                    Role.Doctor,
                    address5
            );
            this.userRepository.saveAll(List.of(mika, zika, jasmina, marta, dragan));
        };
    }
}
