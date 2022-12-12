package main.rest.lookup;

import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Definitions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
class LookupTest {


    @Autowired Lookup SUT;
    @Autowired WordTranslationsRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }


    @Test
    void testLookupThrowsExceptionIfThereIsNothingMatchingTheGivenWord() {
        assertThrows(Exceptions.WordNotFoundException.class, () -> SUT.lookupWord(ACCEPTED_LANGUAGE_1, WORD_OBJECT_1));
    }

    @Test
    void testLookupReturnsEmptyOptionalIfThereIsAMatchToTheGivenWordButInTheWrongLanguage() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertThrows(Exceptions.WordNotFoundException.class, () -> SUT.lookupWord(ACCEPTED_LANGUAGE_2, WORD_OBJECT_1));
    }

    @Test
    void testLookupReturnsTheRightResponseIfThereIsAMatchToTheGivenWord() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        Definitions definitions = SUT.lookupWord(ACCEPTED_LANGUAGE_1, WORD_OBJECT_1);
        assertEquals(WORD_1, definitions.getWord());
        assertEquals(TRANSLATION_SET_1, definitions.getWordDefinitions());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}