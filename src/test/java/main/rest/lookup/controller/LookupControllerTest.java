package main.rest.lookup.controller;

import main.Application;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.UserCredentialsDto;
import main.rest.registration.controller.RegistrationController;
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

import static main.TestConstants.*;
import static main.exception.constants.ExceptionConstants.LANGUAGE_NOT_PROVIDED;
import static main.exception.constants.ExceptionConstants.LANGUAGE_NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LookupControllerTest {

    public static final String URI = "/lookup/WORD_1";
    @LocalServerPort private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired WordTranslationsRepository repository;
    @Autowired RegistrationController controller;

    @BeforeEach
    void setUp() {
        controller.register(new UserCredentialsDto(USERNAME_1, PASSWORD));
    }


    @Test
    void testLookupReturns401UnauthorisedIfTheUserIsNotAuthorised() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI),
                HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLookupReturns400IfLanguageHeaderNotIncluded() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testLookupReturns400IfLanguageHeaderSetToEmpty() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_W_EMPTY_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_PROVIDED, response.getBody());
    }


    @Test
    void testLookupReturns400IfLanguageHeaderSetToWrongLanguage() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_W_WRONG_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_SUPPORTED, response.getBody());
    }

    @Test
    void testLookupReturns204IfWordCannotBeFoundToBeReturned() {
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testLookupReturns200WithTheCorrectWordIfTheWordCanBeFound() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"word\":\"WORD_1\",\"wordDefinitions\":[\"TRANSLATION_2\",\"TRANSLATION_1\"]}", response.getBody());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}