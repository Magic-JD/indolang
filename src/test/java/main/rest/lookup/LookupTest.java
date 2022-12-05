package main.rest.lookup;

import main.database.repository.WordTranslationsRepository;
import main.rest.model.Definitions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testLookupReturnsEmptyOptionalIfThereIsNothingMatchingTheGivenWord() {
        assertTrue(SUT.lookupWord(ACCEPTED_LANGUAGE_1, WORD_OBJECT_1).isEmpty());
    }

    @Test
    void testLookupReturnsEmptyOptionalIfThereIsAMatchToTheGivenWordButInTheWrongLanguage() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        assertTrue(SUT.lookupWord(ACCEPTED_LANGUAGE_2, WORD_OBJECT_1).isEmpty());
    }

    @Test
    void testLookupReturnsTheRightResponseIfThereIsAMatchToTheGivenWord() {
        repository.save(DB_WORD_TRANSLATION_ITEM_1);
        Optional<Definitions> optional = SUT.lookupWord(ACCEPTED_LANGUAGE_1, WORD_OBJECT_1);
        assertTrue(optional.isPresent());
        Definitions definitions = optional.get();
        assertEquals(WORD_1, definitions.getWord());
        assertEquals(TRANSLATION_SET_1, definitions.getWordDefinitions());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}