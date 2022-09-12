package com.example.project.user;

import com.example.project.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
