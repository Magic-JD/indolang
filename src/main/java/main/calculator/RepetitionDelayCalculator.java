package main.calculator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;

@Component
public class RepetitionDelayCalculator {

    @Value("#{${delays_for_correct_answers}}")
    private Map<Integer, Integer> delaysForCorrectAnswers;

    public ZonedDateTime currentZDTWithDelayBeforeRetest(int noOfCorrectAnswers) {
        var time = ZonedDateTime.now();
        Integer delay = delaysForCorrectAnswers.getOrDefault(noOfCorrectAnswers, maximumTimeDelay());
        return time.plusMinutes(delay);
    }

    private int maximumTimeDelay() {
        return delaysForCorrectAnswers.values().stream().mapToInt(i -> i).max().orElse(0);
    }
}
