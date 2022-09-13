package com.example.project.appointment;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "APPOINTMENTS")
public class Appointment {
    @Id
    @SequenceGenerator(
            name = "appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_sequence"
    )
    @Setter(AccessLevel.NONE)
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private AnalysisType analysisType;
    private String doctorEmail;
    private String patientEmail;
}
