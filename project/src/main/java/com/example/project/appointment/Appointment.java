package com.example.project.appointment;

import com.example.project.dto.ScheduleAppointmentDTO;
import com.example.project.address.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @ManyToOne
    private User doctor;
    @ManyToOne
    private User patient;

    public Appointment(ScheduleAppointmentDTO scheduleAppointmentDTO, User patient, User doctor) {
        this.start = LocalDateTime.parse(scheduleAppointmentDTO.getStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end = LocalDateTime.parse(scheduleAppointmentDTO.getEnd(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.doctor = doctor;
        this.patient = patient;
        this.analysisType = AnalysisType.valueOf(scheduleAppointmentDTO.getAnalysisType());
    }
}
