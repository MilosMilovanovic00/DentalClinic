package com.example.project.configuration;

import com.example.project.user.Role;
import com.example.project.user.User;
import com.example.project.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class DataConfig {
    UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            this.userRepository = userRepository;
            //TODO postavi im prave jmbg
            User mika = new User(
                    1L,
                    "Mika",
                    "Mikić",
                    "1056864831542",
                    LocalDate.of(1984, MARCH, 26),
                    "mikamikic@gmail.com",
                    "",
                    Role.Patient
            );
            User zika = new User(
                    2L,
                    "žika",
                    "Žikić",
                    "6178915318645",
                    LocalDate.of(1947, APRIL, 4),
                    "zikazikic@gmail.com",
                    "",
                    Role.Patient
            );
            User jasmina = new User(
                    3L,
                    "Jasnina",
                    "Novaković",
                    "3185485218665",
                    LocalDate.of(1997, DECEMBER, 15),
                    "jasminanovakovic@gmail.com",
                    "",
                    Role.Patient
            );
            User marta = new User(
                    4L,
                    "Marta",
                    "Simić",
                    "4862186201621",
                    LocalDate.of(2004, FEBRUARY, 29),
                    "martasimic@gmail.com",
                    "",
                    Role.Patient
            );
            User dragan = new User(
                    5L,
                    "Dragan",
                    "Marković",
                    "0905197411698",
                    LocalDate.of(1974, JUNE, 9),
                    "draganmarkovic@gmail.com",
                    "",
                    Role.Doctor
            );
            this.userRepository.saveAll(List.of(mika, zika, jasmina, marta, dragan));

        };
    }
}
