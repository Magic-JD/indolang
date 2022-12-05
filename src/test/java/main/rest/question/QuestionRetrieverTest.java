package main.rest.question;

import main.database.model.DbLearnerItem;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testLookupReturnsEmptyOptionalIfThereIsNothingAppropriateInEitherDatabase() {
        assertTrue(true);
        assertTrue(SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1).isEmpty());
    }

    @Test
    void testLookupReturnsEmptyIfThereIsNothingAvailableInTheLearnerRepoAndNothingInTheWordRepoInTheRightLanguage() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertTrue(SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_2).isEmpty());
    }

    @Test
    void testLookupReturnsANewWordFromTheWordTranslationRepositoryIfThereIsNothingAvailableInTheLearnerRepo() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        Optional<Word> optional = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertTrue(optional.isPresent());
        Word word = optional.get();
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
        assertEquals(DB_WORD_TRANSLATION_ITEM_1, dbLearnerItem.getWordTranslation());
        assertTrue(dbLearnerItem.getDate().isAfter(TIME));

    }

    @Test
    void testLookupReturnsANewWordFromTheWordTranslationRepositoryIfThereIsNothingAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_AFTER);
        Optional<Word> optional = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertTrue(optional.isPresent());
        Word word = optional.get();
        assertEquals(WORD_2, word.getWord());
    }

    @Test
    void testLookupReturnsAWordFromTheLearnerRepositoryIfThereIsAWordAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        Optional<Word> optional = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertTrue(optional.isPresent());
        Word word = optional.get();
        assertEquals(WORD_1, word.getWord());
    }

    @Test
    void testLookupReturnsANewWordIfThereIsAWordAvailableInTheLearnerRepoForTheUserQuestioning() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_JUST_BEFORE);
        Optional<Word> optional = SUT.getWord(USERNAME_2, ACCEPTED_LANGUAGE_1);
        assertTrue(optional.isPresent());
        Word word = optional.get();
        assertEquals(WORD_1, word.getWord());
    }

    @Test
    void testLookupReturnsTheMostRecentWordFromTheLearnerRepositoryIfThereAreMultipleAvailableInTheLearnerRepoBeforeTheCurrentTime() {
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_1);
        wordTranslationsRepository.save(DB_WORD_TRANSLATION_ITEM_2);
        learnerRepository.save(DB_LEARNER_ITEM_BEFORE);
        learnerRepository.save(DB_LEARNER_ITEM_JUST_BEFORE);
        Optional<Word> optional = SUT.getWord(USERNAME_1, ACCEPTED_LANGUAGE_1);
        assertTrue(optional.isPresent());
        Word word = optional.get();
        assertEquals(WORD_2, word.getWord());
    }

    @AfterEach
    void tearDown() {
        wordTranslationsRepository.deleteAll();
        learnerRepository.deleteAll();
    }
}