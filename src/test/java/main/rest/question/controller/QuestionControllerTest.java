package main.rest.question.controller;

import main.Application;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.rest.RestControllerTest;
import main.rest.model.Answer;
import main.rest.model.UserCredentialsDto;
import main.rest.registration.controller.RegistrationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static main.TestConstants.*;
import static main.exception.constants.ExceptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionControllerTest extends RestControllerTest {

    public static final String QUESTION_RESPONSE = "{\"word\":\"WORD_1\"}";
    public static final String PASSING_RESPONSE = "{\"pass\":true,\"word\":\"WORD_1\",\"submittedTranslation\":\"TRANSLATION_1\",\"translations\":[]}";
    public static final String FAILING_RESPONSE = "{\"pass\":false,\"word\":\"WORD_1\",\"submittedTranslation\":\"TRANSLATION_3\",\"translations\":[\"TRANSLATION_1\"]}";

    public static final String URI_QUESTION = "/question";
    public static final String URI_ANSWER = "/answer";

    @Autowired WordTranslationsRepository wordRepository;
    @Autowired RegistrationController controller;
    @Autowired LearnerRepository learnerRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        controller.register(new UserCredentialsDto(USERNAME_1, PASSWORD));
    }


    @Test
    void testQuestionReturns401UnauthorisedIfTheUserIsNotAuthorised() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI_QUESTION),
                HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testQuestionReturns400IfLanguageHeaderNotIncluded() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_QUESTION),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testQuestionReturns400IfLanguageHeaderSetToEmpty() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_W_EMPTY_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_QUESTION),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_PROVIDED, response.getBody());
    }


    @Test
    void testQuestionReturns400IfLanguageHeaderSetToWrongLanguage() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_W_WRONG_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_QUESTION),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_SUPPORTED, response.getBody());
    }

    @Test
    void testQuestionReturns206IfWordCannotBeFoundToBeReturned() {
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_QUESTION),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.PARTIAL_CONTENT, response.getStatusCode());
        assertEquals(USER_LEARNED_ALL_WORDS, response.getBody());
    }

    @Test
    void testQuestionReturns200AndWordIfWordIsFoundToBeReturned() {
        wordRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_QUESTION),
                        HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(QUESTION_RESPONSE, response.getBody());
    }


    @Test
    void testAnswerReturns401UnauthorisedIfTheUserIsNotAuthorised() {
        HttpEntity<String> entity = new HttpEntity<>(null, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(URI_ANSWER),
                HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testAnswerReturns400IfBodyNotIncluded() {
        HttpEntity<String> entity = new HttpEntity<>(null, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAnswerReturns400IfLanguageHeaderNotIncluded() {
        HttpEntity<Answer> entity = new HttpEntity<>(CORRECT_ANSWER, HEADERS_WO_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAnswerReturns400IfLanguageHeaderSetToEmpty() {
        HttpEntity<Answer> entity = new HttpEntity<>(CORRECT_ANSWER, HEADERS_W_EMPTY_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_PROVIDED, response.getBody());
    }


    @Test
    void testAnswerReturns400IfLanguageHeaderSetToWrongLanguage() {
        HttpEntity<Answer> entity = new HttpEntity<>(CORRECT_ANSWER, HEADERS_W_WRONG_LANG);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(LANGUAGE_NOT_SUPPORTED, response.getBody());
    }

    @Test
    void testAnswerReturns200AndRightBodyIfAllIsCorrectAndTheRightAnswerIsFound() {
        wordRepository.save(DB_WORD_TRANSLATION_ITEM_1_SMALL);
        learnerRepository.save(DB_LEARNER_ITEM_SMALL);
        HttpEntity<Answer> entity = new HttpEntity<>(CORRECT_ANSWER, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PASSING_RESPONSE, response.getBody());
    }

    @Test
    void testAnswerReturns200AndRightBodyIfTheWrongAnswerIsProvided() {
        wordRepository.save(DB_WORD_TRANSLATION_ITEM_1_SMALL);
        learnerRepository.save(DB_LEARNER_ITEM_SMALL);
        HttpEntity<Answer> entity = new HttpEntity<>(FALSE_ANSWER, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(FAILING_RESPONSE, response.getBody());
    }

    @Test
    void testAnswerThrowsAWordNotLearnedExceptionIfTheLearnerHadNotLearnedThisWordBefore() {
        wordRepository.save(DB_WORD_TRANSLATION_ITEM_1_SMALL);
        HttpEntity<Answer> entity = new HttpEntity<>(FALSE_ANSWER, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(USER_HAS_NOT_LEARNED_WORD, response.getBody());
    }

    @Test
    void testAnswerThrowsATranslationsNotFoundExceptionIfTheTranslationsCannotBeFound() {
        HttpEntity<Answer> entity = new HttpEntity<>(FALSE_ANSWER, CORRECT_HEADERS);
        ResponseEntity<String> response = restTemplate.withBasicAuth(USERNAME_1, PASSWORD)
                .exchange(
                        createURLWithPort(URI_ANSWER),
                        HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}