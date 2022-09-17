package com.example.project.appointment;

import com.example.project.user.User;
import com.example.project.user.UserService;
import com.example.project.dto.AppointmentDTO;
import com.example.project.dto.CancelAppointmentDTO;
import com.example.project.dto.ScheduleAppointmentDTO;
import com.example.project.exceptions.UserNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentsController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/loggedUser")
    public ResponseEntity getAllAppointmentsForLoggedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String email = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(email), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity scheduleAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody ScheduleAppointmentDTO scheduleAppointmentDTO) {
        try {
            String loggedUserEmail = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            String role = userService.getRoleByUsername(loggedUserEmail);
            if (role.equals("Patient") && !loggedUserEmail.equals(scheduleAppointmentDTO.getPatientEmail())) {
                throw new RuntimeException("You have to type in your email as a logged user");
            } else if (role.equals("Doctor") && !loggedUserEmail.equals(scheduleAppointmentDTO.getDoctorEmail())) {
                throw new RuntimeException("You have to type in your email as a logged user");
            }
            User patient = userService.getUserByEmail(scheduleAppointmentDTO.getPatientEmail());
            User doctor = userService.getUserByEmail(scheduleAppointmentDTO.getDoctorEmail());
            appointmentService.scheduleAppointment(scheduleAppointmentDTO, patient, doctor);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(loggedUserEmail), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity cancelAppointmentAsPatient(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody String id) {
        try {
            String loggedUserEmail = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            appointmentService.cancelAppointment(id);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(loggedUserEmail), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cancelAsDoctor")
    public ResponseEntity cancelAppointmentAsDoctor(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody CancelAppointmentDTO cancelAppointmentDTO) {
        try {
            String loggedUserEmail = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            User patient;
            if(cancelAppointmentDTO.isPatientChecked()) {
                patient=userService.getUserByEmail(cancelAppointmentDTO.getPatientEmail());
                if(patient==null)
                    throw new UserNotFound("There is no patient in database with this email");
                if (loggedUserEmail.equals(cancelAppointmentDTO.getDoctorEmail()))
                    appointmentService.cancelAppointmentAsDoctor(cancelAppointmentDTO.getId(),patient);
            }else{
                appointmentService.cancelAppointment(cancelAppointmentDTO.getId());
            }
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(loggedUserEmail), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
