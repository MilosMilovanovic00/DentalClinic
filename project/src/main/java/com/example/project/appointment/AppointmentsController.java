package com.example.project.appointment;

import com.example.project.dto.AppointmentDTO;
import com.example.project.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
