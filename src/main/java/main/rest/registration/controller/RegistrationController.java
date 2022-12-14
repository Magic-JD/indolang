package main.rest.registration.controller;

import main.database.model.DbUserItem;
import main.database.repository.UserRepository;
import main.rest.model.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${registration_roles}") private Set<String> roles;

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody UserCredentialsDto userCredentialsDto) {
        if (userIsAlreadyRegistered(userCredentialsDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            var user = new DbUserItem(userCredentialsDto.getUsername(), passwordEncoder.encode(userCredentialsDto.getPassword()), true, roles);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    private boolean userIsAlreadyRegistered(UserCredentialsDto userCredentialsDto) {
        return userRepository.findItemByName(userCredentialsDto.getUsername()).isPresent();
    }
}
