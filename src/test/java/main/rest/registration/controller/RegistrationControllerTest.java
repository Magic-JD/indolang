package main.rest.registration.controller;

import main.Application;
import main.database.model.DbUserItem;
import main.database.repository.UserRepository;
import main.rest.model.UserCredentialsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerTest {

    @LocalServerPort private int port;
    public static final String URI = "/registration";

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testRegistrationSavesNewUserIfNotRegisteredWithCorrectRoles() {
        HttpEntity<UserCredentialsDto> entity = new HttpEntity<>(USER_CREDENTIALS_DTO, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<DbUserItem> users = repository.findAll();
        assertEquals(1, users.size());
        DbUserItem user = users.get(0);
        assertNotEquals(PASSWORD, user.getPassword());
        assertEquals(USERNAME_1, user.getUsername());
        assertEquals(ROLES_SET_1, user.getRoles());
    }

    @Test
    void testRegistrationReturnsConflictIfUsernameIsAlreadyTaken() {
        repository.save(DB_USER_ITEM);
        HttpEntity<UserCredentialsDto> entity = new HttpEntity<>(USER_CREDENTIALS_DTO, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
