package main.rest.update.controller;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.rest.RestControllerTest;
import main.rest.model.Definition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static main.TestConstants.*;
import static main.exception.constants.ExceptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateControllerTest extends RestControllerTest {

    public static final String URI_CREATE = "/update/create";
    public static final String URI_DELETE = "/update/delete";

    @Autowired WordTranslationsRepository wordRepository;

    @Test
    void testCreateReturns401UnauthorisedIfTheUserIsNotAuthorisedAtAll() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI_CREATE),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCreateReturns403UnauthorisedIfTheUserIsOnlyPartiallyAuthorised() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD).exchange(
                createURLWithPort(URI_CREATE),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testCreateReturns400IfBodyNotIncluded() {
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_CREATE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateReturns400IfLanguageHeaderNotIncluded() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_CREATE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateReturns400IfLanguageHeaderSetToEmpty() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_W_EMPTY_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_CREATE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testCreateReturns400IfLanguageHeaderSetToWrongLanguage() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_W_WRONG_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_CREATE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_SUPPORTED, response.getBody());
    }

    @Test
    void testCreateReturns201IfAllIsCorrectTheDatabaseIsUpdated() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_CREATE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<DbWordTranslationsItem> words = wordRepository.findAll();
        assertEquals(1, words.size());
        DbWordTranslationsItem word = words.get(0);
        assertEquals(WORD_1, word.getKeyWord());
        assertEquals(TRANSLATION_SET_1_SMALL, word.getTranslations());
        assertEquals(ACCEPTED_LANGUAGE_1, word.getLocale());
    }

    @Test
    void testDeleteReturns401UnauthorisedIfTheUserIsNotAuthorisedAtAll() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI_DELETE),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteReturns403UnauthorisedIfTheUserIsOnlyPartiallyAuthorised() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD).exchange(
                createURLWithPort(URI_DELETE),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testDeleteReturns400IfBodyNotIncluded() {
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteReturns400IfLanguageHeaderNotIncluded() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteReturns400IfLanguageHeaderSetToEmpty() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_W_EMPTY_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_PROVIDED, response.getBody());
    }


    @Test
    void testDeleteReturns400IfLanguageHeaderSetToWrongLanguage() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, HEADERS_W_WRONG_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_SUPPORTED, response.getBody());
    }

    @Test
    void testDeleteReturnsUnprocessableEntityIfWordDoesNotExistToBeRemoved() {
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(WORD_CANNOT_BE_REMOVED, response.getBody());
    }

    @Test
    void testDeleteReturns204IfAllIsCorrectTheDatabaseIsUpdated() {
        wordRepository.save(DB_WORD_TRANSLATION_ITEM_1_SMALL);
        HttpEntity<Definition> entity = new HttpEntity<>(DEFINITION_1, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_2, PASSWORD)
                .exchange(
                        createURLWithPort(URI_DELETE),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        List<DbWordTranslationsItem> words = wordRepository.findAll();
        assertTrue(words.isEmpty());
    }
}