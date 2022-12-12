package main.rest.question;

import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Word;
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
class QuestionRetrieverTest {

    @Autowired QuestionRetriever SUT;
    @Autowired LearnerRepository learnerRepository;
    @Autowired WordTranslationsRepository wordTranslationsRepository;

    @BeforeEach
    void setUp() {
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }


    @Test
    void testLookupThrowsExceptionIfThereIsNothingAppropriateInEitherDatabase() {
        assertThrows(Exceptions.AllWordsLearnedException.class, () -> SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1));
    }

    @Test
    void testLookupThrowsExceptionIfThereIsNothingAvailableInTheLearnerRepoAndNothingInTheWordRepoInTheRightLanguage() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertThrows(Exceptions.AllWordsLearnedException.class, () -> SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_2));
    }

    @Test
    void testLookupReturnsANewWordFromTheWordTranslationRepositoryIfThereIsNothingAvailableInTheLearnerRepo() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        Word word = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertEquals(WORD_1, word.getWord());
    }

    @Test
    void testLookupSavesTheWordInTheLearnerRepoIfANewWordWasFound() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertTrue(learnerRepository.findAll().isEmpty());
        SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        List<DbLearnerItem> learnerItems = learnerRepository.findAll();
        assertEquals(1, learnerItems.size());
        DbLearnerItem dbLearnerItem = learnerItems.get(0);
        assertEquals(USERNAME_1, dbLearnerItem.getUsername());
        assertEquals(0, dbLearnerItem.getSuccessfulAnswers());
        DbWordTranslationsItem wordTranslation = dbLearnerItem.getWordTranslation();
        assertEquals(WORD_1, wordTranslation.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordTranslation.getLocale());
        assertEquals(TRANSLATION_SET_1, wordTranslation.getTranslations());
        assertEquals(WT_ID_1.toString(), wordTranslation.get_id().toString());
        assertTrue(dbLearnerItem.getDate().isAfter(TIME));

    }

    @Test
    void testLookupReturnsANewWordFromTheWordTranslationRepositoryIfThereIsNothingAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_AFTER);
        Word word = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertEquals(WORD_2, word.getWord());
    }

    @Test
    void testLookupReturnsAWordFromTheLearnerRepositoryIfThereIsAWordAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        Word word = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertEquals(WORD_1, word.getWord());
    }

    @Test
    void testLookupReturnsANewWordIfThereIsAWordAvailableInTheLearnerRepoForTheUserQuestioning() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_JUST_BEFORE);
        Word word = SUT.getWord(USERNAME_2, ACCEPTED_LANGUAGE_1);
        assertEquals(WORD_1, word.getWord());
    }

    @Test
    void testLookupReturnsTheMostRecentWordFromTheLearnerRepositoryIfThereAreMultipleAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        learnerRepository.save(DB_LEARNER_ITEM_JUST_BEFORE);
        Word word = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertEquals(WORD_2, word.getWord());
    }

    @AfterEach
    void tearDown() {
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }
}