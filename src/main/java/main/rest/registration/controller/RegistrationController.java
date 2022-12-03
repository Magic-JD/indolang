package main.rest.registration.controller;

import main.database.model.DbUserDetails;
import main.database.repository.UserRepository;
import main.rest.registration.data.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RegistrationController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody UserCredentialsDto userCredentialsDto) {
        if (userRepository.findItemByName(userCredentialsDto.getUsername()) == null) {
            var user = new DbUserDetails(userCredentialsDto.getUsername(), passwordEncoder.encode(userCredentialsDto.getPassword()), true, Set.of("USER"));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
