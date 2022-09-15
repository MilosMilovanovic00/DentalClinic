package com.example.project.appointment;

import com.example.project.dto.AppointmentDTO;
import com.example.project.dto.ScheduleAppointmentDTO;
import com.example.project.user.User;
import com.example.project.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentsController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/loggedUser")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForLoggedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String email = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(email), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<List<AppointmentDTO>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<List<AppointmentDTO>> scheduleAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody ScheduleAppointmentDTO scheduleAppointmentDTO) {
        try {
            String loggedUserEmail = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            User patient = userService.getUserByEmail(scheduleAppointmentDTO.getPatientEmail());
            User doctor = userService.getUserByEmail(scheduleAppointmentDTO.getDoctorEmail());
            appointmentService.scheduleAppointment(scheduleAppointmentDTO, patient, doctor);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(loggedUserEmail), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<List<AppointmentDTO>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<List<AppointmentDTO>> cancelAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String id) {
        try {
            String loggedUserEmail = userService.getUsernameFromAuthorizationHeader(authorizationHeader);
            appointmentService.cancelAppointment(id);
            return new ResponseEntity<List<AppointmentDTO>>(appointmentService.getAllAppointmentsForLoggedUser(loggedUserEmail), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<List<AppointmentDTO>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

    }
}
