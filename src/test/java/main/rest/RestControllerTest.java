package main.rest;

import main.Application;
import main.database.model.DbUserItem;
import main.database.repository.LearnerRepository;
import main.database.repository.UserRepository;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.UserCredentialsDto;
import main.rest.registration.controller.RegistrationController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static main.TestConstants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

    @LocalServerPort private int port;
    public TestRestTemplate restTemplate = new TestRestTemplate();
    public static final String HASH = "$2a$10$.lHmsVVawmHMCOtVk5RMiOzmVJCUk9PVfYSh5urKRZ3G7TwCmwxKG";

    @Autowired UserRepository userRepository;
    @Autowired WordTranslationsRepository wordTranslationsRepository;
    @Autowired LearnerRepository learnerRepository;
    @Autowired RegistrationController controller;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
        controller.register(new UserCredentialsDto(USERNAME_1, PASSWORD));
        userRepository.save(new DbUserItem(USERNAME_2, HASH, true, ROLES_SET_2));
    }

    public String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }


}
