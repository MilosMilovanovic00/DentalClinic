package com.example.project.user;

import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.UserDTO;
import com.example.project.exceptions.UserNotFound;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Value("spring-security")
    private String APP_NAME;
    @Value("some-secret")
    public String SECRET;
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public LoginResponseDTO login(String email) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        User user = getUserByEmail(email);
        if (user == null)
            throw new UserNotFound("This user doesn't exists");
        String token = Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
        loginResponseDTO.setUserId(String.valueOf(user.getId()));
        loginResponseDTO.setMessage("You successfully have log in");
        loginResponseDTO.setToken(token);
        return loginResponseDTO;
    }

    public UserDTO getUserData(String id) {
        User user = userRepository.getUserById(Long.valueOf(id));
        if (user == null) {
            throw new UserNotFound("User with this id doesn't exist");
        }
        return new UserDTO(user);
    }


//    @Transactional ide u servis
}
