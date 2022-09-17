package com.example.project.user;

import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody String email) {
        try {
            return ResponseEntity.ok().body(userService.login(email));
        } catch (Exception e) {
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO("", e.getMessage(), "-1");
            return new ResponseEntity<LoginResponseDTO>(loginResponseDTO, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        return new ResponseEntity<>(userService.getLoggedUserRole(authorizationHeader),HttpStatus.OK);
    }
    @GetMapping("/data/{id}")
    public ResponseEntity<UserDTO> getDataFOrUser(@PathVariable String id) {
        try {
            return new ResponseEntity<UserDTO>(userService.getUserData(id), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<UserDTO>(new UserDTO(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/emails/{role}")
    public ResponseEntity<ArrayList<String>> getEmails(@PathVariable String role) {
        return new ResponseEntity<>(userService.getEmails(role), HttpStatus.OK);
    }

}
