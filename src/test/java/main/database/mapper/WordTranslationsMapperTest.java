package main.database.mapper;

import main.database.model.DbWordTranslationsItem;
import main.rest.model.Definitions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordTranslationsMapperTest {

    WordTranslationsMapper SUT = new WordTranslationsMapper();
    String keyword = "KEYWORD";
    String en = "en";
    ObjectId id = new ObjectId();
    Set<String> translations = Set.of("translation");
    DbWordTranslationsItem item = new DbWordTranslationsItem(en, keyword);

    @BeforeEach
    void setUp() {
        item.set_id(id);
        item.setTranslations(translations);
    }

    @Test
    void testConvertsToDefinitionsCorrectly() {
        Definitions definitions = SUT.toDefinitions(item);
        assertEquals(keyword, definitions.getWord());
        assertEquals(translations, definitions.getWordDefinitions());
    }

    @Test
    void testConvertsToTranslationsCorrectly() {
        assertEquals(translations, SUT.toTranslations(item));
    }

    @Test
    void toWord() {
        assertEquals(keyword, SUT.toWord(item));
    }

    @Test
    void toId() {
        assertEquals(id, SUT.toId(item));
    }
}