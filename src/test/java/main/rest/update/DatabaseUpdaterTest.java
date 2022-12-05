package main.rest.update;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
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
class DatabaseUpdaterTest {

    @Autowired DatabaseUpdater SUT;
    @Autowired WordTranslationsRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testThatANewWordCanBeAddedToTheDatabase() {
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertEquals(1, words.size());
        DbWordTranslationsItem wordItem = words.get(0);
        assertEquals(WORD_1, wordItem.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordItem.getLocale());
        assertEquals(TRANSLATION_SET_1_SMALL, wordItem.getTranslations());
    }

    @Test
    void testThatANewWordCanBeAddedToTheDatabaseAndThenTheTranslationsCanBeAddedToForSameKeyWord() {
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1);
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_2);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertEquals(1, words.size());
        DbWordTranslationsItem wordItem = words.get(0);
        assertEquals(WORD_1, wordItem.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordItem.getLocale());
        assertEquals(TRANSLATION_SET_1, wordItem.getTranslations());
    }

    @Test
    void testThatTwoNewWordCanBeAddedToTheDatabaseAndThenTheTranslationsCanBeAddedForDifferentKeyWords() {
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1);
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_3);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertEquals(2, words.size());
        DbWordTranslationsItem wordItem1 = words.stream().filter(i -> i.getKeyWord().equals(WORD_1)).findAny().get();
        DbWordTranslationsItem wordItem2 = words.stream().filter(i -> i.getKeyWord().equals(WORD_2)).findAny().get();
        assertEquals(WORD_1, wordItem1.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordItem1.getLocale());
        assertEquals(TRANSLATION_SET_1_SMALL, wordItem1.getTranslations());
        assertEquals(WORD_2, wordItem2.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordItem2.getLocale());
        assertEquals(TRANSLATION_SET_2_SMALL, wordItem2.getTranslations());
    }

    @Test
    void testThatTwoWordsCanBeAddedWithTheSameKeywordIfTheyAreAddedForDifferentLocations() {
        SUT.updateDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1);
        SUT.updateDatabase(ACCEPTED_LANGUAGE_2, DEFINITION_1);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertEquals(2, words.size());
        DbWordTranslationsItem wordItem1 = words.stream().filter(i -> i.getLocale().equals(ACCEPTED_LANGUAGE_1)).findAny().get();
        DbWordTranslationsItem wordItem2 = words.stream().filter(i -> i.getLocale().equals(ACCEPTED_LANGUAGE_2)).findAny().get();
        assertEquals(WORD_1, wordItem1.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, wordItem1.getLocale());
        assertEquals(TRANSLATION_SET_1_SMALL, wordItem1.getTranslations());
        assertEquals(WORD_1, wordItem2.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_2, wordItem2.getLocale());
        assertEquals(TRANSLATION_SET_1_SMALL, wordItem2.getTranslations());
    }

    @Test
    void testThatRemovingAWordThatIsNotInTheDatabaseThrowsAWordDoesNotExistToBeRemoved() {
        assertThrows(Exceptions.WordDoesNotExistToBeRemoved.class, () -> SUT.removeFromDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1));
    }

    @Test
    void testThatRemovingAWordThatIsInTheDatabaseThrowsAWordDoesNotExistToBeRemovedIfTheWrongLanguageIsGiven() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertThrows(Exceptions.WordDoesNotExistToBeRemoved.class, () -> SUT.removeFromDatabase(ACCEPTED_LANGUAGE_2, DEFINITION_1));
    }

    @Test
    void testThatRemovingAWordThatIsInTheDatabaseThrowsAWordDoesNotExistToBeRemovedIfTheTranslationIsNotContainedInTheWordsTranslationList() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertThrows(Exceptions.WordDoesNotExistToBeRemoved.class, () -> SUT.removeFromDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_4));
    }

    @Test
    void testThatTheProgramWillRemoveOnlyTheGivenTranslationIfTheWordHasMultipleTranslations() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        SUT.removeFromDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_2);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertEquals(1, words.size());
        DbWordTranslationsItem word = words.get(0);
        assertEquals(WORD_1, word.getKeyWord());
        assertEquals(ACCEPTED_LANGUAGE_1, word.getLocale());
        assertEquals(TRANSLATION_SET_1_SMALL, word.getTranslations());
    }

    @Test
    void testThatTheProgramWillRemoveTheWholeWordIfAllTranslationsAreRemoved() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        SUT.removeFromDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_1);
        SUT.removeFromDatabase(ACCEPTED_LANGUAGE_1, DEFINITION_2);
        List<DbWordTranslationsItem> words = repository.findAll();
        assertTrue(words.isEmpty());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}