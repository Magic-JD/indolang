package main.database.model;

import org.junit.jupiter.api.Test;

import static main.TestConstants.NUMBER;
import static main.TestConstants.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DbLearnerItemTest {

    DbLearnerItem SUT = new DbLearnerItem(null, null, null, NUMBER);

    @Test
    void testUpdatingSuccessfulAnswersCorrectlyUpdatesTheNumber() {
        assertEquals(NUMBER, SUT.getSuccessfulAnswers());
        SUT.updateSuccessfulAnswers(true);
        assertEquals(NUMBER + 1, SUT.getSuccessfulAnswers());
        SUT.updateSuccessfulAnswers(false);
        assertEquals(ZERO, SUT.getSuccessfulAnswers());
    }
}