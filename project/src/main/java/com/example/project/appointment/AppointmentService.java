package com.example.project.appointment;

import com.example.project.dto.AppointmentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentsRepository appointmentsRepository;

    public ArrayList<AppointmentDTO> getAllAppointmentsForLoggedUser(String email) {
        ArrayList<AppointmentDTO> list = new ArrayList<>();
        for (Appointment appointment :
                appointmentsRepository.findAll()) {
            if (appointment.getDoctor().getEmail().equals(email) || appointment.getPatient().getEmail().equals(email))
                list.add(new AppointmentDTO(appointment));
        }
        return list;
    }
}
