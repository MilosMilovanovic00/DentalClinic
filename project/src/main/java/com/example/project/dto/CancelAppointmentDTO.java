package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancelAppointmentDTO {
    private boolean patientChecked;
    private String patientEmail;
    private String doctorEmail;
    private String id;
}
