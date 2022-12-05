package main.database.mapper;

import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import org.junit.jupiter.api.Test;

import static main.TestConstants.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LearnerMapperTest {

    LearnerMapper SUT = new LearnerMapper();

    DbWordTranslationsItem wordItem = new DbWordTranslationsItem("en", "hello");
    DbLearnerItem learnerItem = new DbLearnerItem(wordItem, "username", TIME, 0);

    @Test
    void testTransfersFromLearnerToWordItem() {
        assertEquals(SUT.toWordItem(learnerItem), wordItem);
    }
}