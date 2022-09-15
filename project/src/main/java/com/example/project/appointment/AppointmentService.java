package com.example.project.appointment;

import com.example.project.dto.AppointmentDTO;
import com.example.project.dto.ScheduleAppointmentDTO;
import com.example.project.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void scheduleAppointment(ScheduleAppointmentDTO scheduleAppointmentDTO, User patient, User doctor) {
        LocalDateTime start = LocalDateTime.parse(scheduleAppointmentDTO.getStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(scheduleAppointmentDTO.getEnd(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Appointment appointment = new Appointment();
        appointment.setEnd(end);
        appointment.setStart(start);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAnalysisType(AnalysisType.valueOf(scheduleAppointmentDTO.getAnalysisType()));
        appointmentsRepository.save(appointment);
    }

    public void cancelAppointment(String id) {
        Appointment appointment = appointmentsRepository.getById(Long.valueOf(id));
        appointmentsRepository.delete(appointment);
    }
}
