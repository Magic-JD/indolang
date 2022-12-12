package main.rest.question;

import main.database.model.DbLearnerItem;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class QuestionVerifierTest {

    @Autowired QuestionVerifier SUT;
    @Autowired LearnerRepository learnerRepository;
    @Autowired WordTranslationsRepository wordTranslationsRepository;

    @BeforeEach
    void setUp() {
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }

    @Test
    void testThatIfTheWordCanNotBeFoundInTranslationsItWillThrowATranslationsNotFoundException() {
        assertThrows(Exceptions.TranslationsNotFoundException.class, () -> SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, CORRECT_ANSWER));
    }

    @Test
    void testThatIfTheWordCanNotBeFoundInLearnerItWillThrowAWordNotLearnedException() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertThrows(Exceptions.WordNotLearnedException.class, () -> SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, CORRECT_ANSWER));
    }

    @Test
    void testThatIfTheWordCanBeFoundInLearnerItReturnCorrectForACorrectResult() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        Result result = SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, CORRECT_ANSWER);
        assertTrue(result.isPass());
        assertEquals(WORD_1, result.getWord());
        assertEquals(TRANSLATION_1, result.getSubmittedTranslation());
        assertEquals(EMPTY_SET, result.getCorrectTranslations());
    }

    @Test
    void testThatIfTheCorrectAnswerIsInputItUpdatesTheTimeAndCorrectAnswers() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, CORRECT_ANSWER);
        List<DbLearnerItem> allForLearner = learnerRepository.findAllForLearner(USERNAME_1);
        assertEquals(1, allForLearner.size());
        DbLearnerItem dbLearnerItem = allForLearner.get(0);
        assertEquals(NUMBER + 1, dbLearnerItem.getSuccessfulAnswers());
        assertTrue(dbLearnerItem.getDate().isAfter(TIME));
    }

    @Test
    void testThatIfTheWordCanBeFoundInLearnerItReturnFalseForAFalseResult() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        Result result = SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, FALSE_ANSWER);
        assertFalse(result.isPass());
        assertEquals(WORD_1, result.getWord());
        assertEquals(TRANSLATION_3, result.getSubmittedTranslation());
        assertEquals(TRANSLATION_SET_1, result.getCorrectTranslations());
    }

    @Test
    void testThatIfTheIncorrectAnswerIsInputItUpdatesTheTimeAndCorrectAnswers() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_1, FALSE_ANSWER);
        List<DbLearnerItem> allForLearner = learnerRepository.findAllForLearner(USERNAME_1);
        assertEquals(1, allForLearner.size());
        DbLearnerItem dbLearnerItem = allForLearner.get(0);
        assertEquals(0, dbLearnerItem.getSuccessfulAnswers());
        assertTrue(dbLearnerItem.getDate().isAfter(TIME));
    }

    @Test
    void testThatIfTheCorrectAnswerIsInputInTheWrongLanguageItThrowsAWordNotFoundException() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        assertThrows(Exceptions.TranslationsNotFoundException.class, () -> SUT.verifyTest(USERNAME_1, ACCEPTED_LANGUAGE_2, CORRECT_ANSWER));

    }

    @Test
    void testThatIfTheCorrectAnswerIsInputForTheWrongUserItThrowsAWordNotLearnedException() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        assertThrows(Exceptions.WordNotLearnedException.class, () -> SUT.verifyTest(USERNAME_2, ACCEPTED_LANGUAGE_1, CORRECT_ANSWER));

    }


    @AfterEach
    void tearDown() {
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }
}