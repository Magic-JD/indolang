package main.registration;

import main.database.model.DbUserDetails;
import main.database.repository.UserRepository;
import main.registration.data.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class RegistrationResource {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserCredentialsDto userCredentialsDto) {
        DbUserDetails user = new DbUserDetails(userCredentialsDto.getUsername(), passwordEncoder.encode(userCredentialsDto.getPassword()), true, Set.of("USER"));
        userRepository.save(user);
    }
}
