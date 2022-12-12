package main.rest.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;


class ResultTest {

    @Test
    void createPassingResult() {
        Result passingResult = Result.createPassingResult(CORRECT_ANSWER);
        assertTrue(passingResult.isPass());
        assertEquals(WORD_1, passingResult.getWord());
        assertEquals(TRANSLATION_1, passingResult.getSubmittedTranslation());
        assertEquals(Collections.emptySet(), passingResult.getCorrectTranslations());
    }

    @Test
    void createFailingResult() {
        Result failingResult = Result.createFailingResult(FALSE_ANSWER, TRANSLATION_SET_1);
        assertFalse(failingResult.isPass());
        assertEquals(WORD_1, failingResult.getWord());
        assertEquals(TRANSLATION_3, failingResult.getSubmittedTranslation());
        assertEquals(TRANSLATION_SET_1, failingResult.getCorrectTranslations());
    }
}