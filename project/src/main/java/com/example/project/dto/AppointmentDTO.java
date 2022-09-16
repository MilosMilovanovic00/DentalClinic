package com.example.project.dto;

import com.example.project.appointment.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDTO {
    private Long id;
    private String start;
    private String end;
    private String title;
    private String doctorEmail;
    private String patientEmail;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.start = appointment.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end = appointment.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.title = appointment.getAnalysisType().name();
        this.doctorEmail = appointment.getDoctor().getEmail();
        this.patientEmail = appointment.getPatient().getEmail();
    }
}
