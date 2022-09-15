package com.example.project.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.project.dto.LoginResponseDTO;
import com.example.project.dto.UserDTO;
import com.example.project.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null)
            throw new UserNotFound("User with this email doesn't exist");
        return user;
    }

    public LoginResponseDTO login(String email) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        User user = getUserByEmail(email);
        if (user == null)
            throw new UserNotFound("This user doesn't exists");
        Algorithm algorithm = Algorithm.HMAC256(("secret".getBytes()));
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 24 * 60 * 1000))
                .sign(algorithm);
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

    public ArrayList<String> getEmails(String role) {
        ArrayList<String> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (!user.getRole().name().equals(role)) {
                list.add(user.getEmail());
            }
        }
        return list;
    }

    public String getLoggedUserRole(String authorizationHeader) {
        String email = getUsernameFromAuthorizationHeader(authorizationHeader);
        if (email == null)
            throw new UserNotFound("There isn't logged user");
        User user = userRepository.getUserByEmail(email);
        return user.getRole().name();

    }

    public String getUsernameFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256(("secret".getBytes()));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } else {
            throw new UserNotFound("There isn't logged user");
        }
    }

}
