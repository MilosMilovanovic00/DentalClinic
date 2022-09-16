package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleAppointmentDTO {
    private String patientEmail;
    private String doctorEmail;
    private String start;
    private String end;
    private String analysisType;
}