package main.rest.registration.controller;

import main.database.model.DbUserItem;
import main.database.repository.UserRepository;
import main.rest.RestControllerTest;
import main.rest.model.UserCredentialsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class RegistrationControllerTest extends RestControllerTest {

    public static final String URI = "/registration";

    @Autowired UserRepository repository;

    @Test
    void testRegistrationSavesNewUserIfNotRegisteredWithCorrectRoles() {
        repository.deleteAll();
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
        repository.deleteAll();
        repository.save(DB_USER_ITEM);
        HttpEntity<UserCredentialsDto> entity = new HttpEntity<>(USER_CREDENTIALS_DTO, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
