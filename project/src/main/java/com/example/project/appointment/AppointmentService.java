package com.example.project.appointment;

import com.example.project.user.User;
import com.example.project.configuration.email.EmailService;
import com.example.project.dto.AppointmentDTO;
import com.example.project.dto.ScheduleAppointmentDTO;
import com.example.project.exceptions.UnsuccessfulScheduling;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentsRepository appointmentsRepository;
    private final EmailService emailService;

    public ArrayList<AppointmentDTO> getAllAppointmentsForLoggedUser(String email) {
        ArrayList<AppointmentDTO> list = new ArrayList<>();
        appointmentsRepository.findAllWithEmail(email).forEach(appointment -> {
            list.add(new AppointmentDTO(appointment));
        });
        return list;
    }

    public void scheduleAppointment(ScheduleAppointmentDTO scheduleAppointmentDTO, User patient, User doctor) throws UnsuccessfulScheduling {
        Appointment newAppointment = new Appointment(scheduleAppointmentDTO, patient, doctor);
        List<Appointment> appointments = appointmentsRepository.findAllOnDate(newAppointment.getStart(), newAppointment.getEnd());
        if (appointments.isEmpty()) {
            appointmentsRepository.save(newAppointment);
            sendScheduledEmail(patient, doctor, newAppointment);
        } else
            throw new UnsuccessfulScheduling("The period of this appointment is already taken");

    }

    private void sendScheduledEmail(User patient, User doctor, Appointment newAppointment) {
        String patientMessage = "You have scheduled an appointment at our dental clinic at " +
                newAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm EE dd MMMM yyyy")) + ". Your doctor will be " + doctor.getFullName() + ".";
        String doctorMessage = "You have scheduled an appointment at your office at " +
                newAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm EE dd MMMM yyyy")) + ". Your patient will be " + patient.getFullName() + ".";
        String patientEmail = emailService.buildString(EmailService.EmailTitle.NoticeOnScheduled, EmailService.EmailMessageTitle.MessageTitleOnScheduled, patientMessage);
        String doctorEmail = emailService.buildString(EmailService.EmailTitle.NoticeOnScheduled, EmailService.EmailMessageTitle.MessageTitleOnScheduled, doctorMessage);
        emailService.send(patient.getEmail(), patientEmail, "Scheduled appointment");
        emailService.send(doctor.getEmail(), doctorEmail, "Scheduled appointment");
    }

    public void sendCancellationEmail(Appointment appointment) {
        String patientMessage = "You have canceled an appointment at our dental clinic at " +
                appointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm EE dd of MMMM yyyy.")) + ". We are sorry you canceled. Please reach us if you need something.";
        String doctorMessage = "Patient " + appointment.getPatient().getFullName() + " cancelled an appointment at " +
                appointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm EE dd of MMMM yyyy.")) + ". Will we send email if it comes to any changes.";
        String patientEmail = emailService.buildString(EmailService.EmailTitle.NoticeOnScheduled, EmailService.EmailMessageTitle.MessageTitleOnScheduled, patientMessage);
        String doctorEmail = emailService.buildString(EmailService.EmailTitle.NoticeOnScheduled, EmailService.EmailMessageTitle.MessageTitleOnScheduled, doctorMessage);
        emailService.send(appointment.getPatient().getEmail(), patientEmail, "Scheduled appointment");
        emailService.send(appointment.getDoctor().getEmail(), doctorEmail, "Scheduled appointment");
    }

    public void cancelAppointment(String id) {
        Appointment appointment = appointmentsRepository.getById(Long.valueOf(id));
        appointmentsRepository.delete(appointment);
        sendCancellationEmail(appointment);
    }

    public void cancelAppointmentAsDoctor(String id, User patient) {
        Appointment appointment = appointmentsRepository.getById(Long.valueOf(id));
        if (!appointment.getPatient().getEmail().equals(patient.getEmail()))
            throw new RuntimeException("You can't delete appointment for the inputted user");
        appointmentsRepository.delete(appointment);
        sendCancellationEmail(appointment);
    }
}
