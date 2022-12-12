package main.calculator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
class RepetitionDelayCalculatorTest {

    @Autowired RepetitionDelayCalculator repetitionDelayCalculator;

    @Test
    void testRepetitionDelayCalculatorAddsAppropriateDelayForZeroCorrectAnswers() {
        ZonedDateTime timeWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(0);
        ZonedDateTime currentTime = ZonedDateTime.now();
        assertAccurateWithinOneSecond(timeWithDelay, currentTime, 10);
    }

    @Test
    void testRepetitionDelayCalculatorAddsAppropriateDelayForOneCorrectAnswers() {
        ZonedDateTime timeWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(1);
        ZonedDateTime currentTime = ZonedDateTime.now();
        assertAccurateWithinOneSecond(timeWithDelay, currentTime, 11);
    }

    @Test
    void testRepetitionDelayCalculatorAddsAppropriateDelayForThreeCorrectAnswers() {
        ZonedDateTime timeWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(3);
        ZonedDateTime currentTime = ZonedDateTime.now();
        assertAccurateWithinOneSecond(timeWithDelay, currentTime, 13);
    }

    @Test
    void testRepetitionDelayCalculatorAddsAppropriateDelayForFiveCorrectAnswers() {
        ZonedDateTime timeWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(5);
        ZonedDateTime currentTime = ZonedDateTime.now();
        assertAccurateWithinOneSecond(timeWithDelay, currentTime, 15);
    }

    @Test
    void testRepetitionDelayCalculatorAddsAppropriateDelayForOverFiveCorrectAnswers() {
        ZonedDateTime timeWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(10);
        ZonedDateTime currentTime = ZonedDateTime.now();
        assertAccurateWithinOneSecond(timeWithDelay, currentTime, 15);
    }

    private static void assertAccurateWithinOneSecond(ZonedDateTime timeWithDelay, ZonedDateTime currentTime, int minuteDelay) {
        assertTrue(timeWithDelay.isAfter(currentTime.plusMinutes(minuteDelay - 1).plusSeconds(59)));
        assertTrue(timeWithDelay.isBefore(currentTime.plusMinutes(minuteDelay).plusSeconds(1)));
    }

}