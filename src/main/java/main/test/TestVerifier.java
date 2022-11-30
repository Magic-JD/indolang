package main.test;

import main.test.data.Answer;
import main.test.data.Result;
import main.wordset.WordData;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TestVerifier {

    @Autowired
    WordsetCompiler wordsetCompiler;

    public Optional<Result> verifyTestEnglish(Answer answer) {
        return verifyTest(wordsetCompiler.getWordDataEnglish(), answer);
    }

    public Optional<Result> verifyTestIndonesian(Answer answer) {
        return verifyTest(wordsetCompiler.getWordDataIndonesian(), answer);
    }

    private Optional<Result> verifyTest(List<WordData> wordDataList, Answer answer) {
        Optional<WordData> matchingData = wordDataList.stream().filter(wordData -> wordData.getKeyWord().equals(answer.getAskedQuestion())).findFirst();
        return matchingData.map(data -> {
            if (data.getTranslations().contains(answer.getAnswer())) {
                data.setSucessfulAnswers(data.getSucessfulAnswers() + 1);
                data.setDate(calulateDate(data.getSucessfulAnswers()));
                return new Result(true,
                        String.format("Correct, the translation of: '%s' is: '%s'.",
                                answer.getAskedQuestion(),
                                answer.getAnswer()));
            } else {
                data.setSucessfulAnswers(0);
                data.setDate(calulateDate(data.getSucessfulAnswers()));
                return new Result(false,
                        String.format("Unfortunately '%s' is not the correct translation of '%s'. You should have chosen one of %s.",
                                answer.getAnswer(),
                                answer.getAskedQuestion(),
                                String.join(", ", data.getTranslations())));
            }
        });
    }

    private ZonedDateTime calulateDate(int noOfSucessfulAnswers) {
        ZonedDateTime time = ZonedDateTime.now();
        if (noOfSucessfulAnswers == 0) {
            return time.plusMinutes(5);
        } else if (noOfSucessfulAnswers == 1) {
            return time.plusHours(4);
        } else if (noOfSucessfulAnswers == 2) {
            return time.plusDays(4);
        } else if (noOfSucessfulAnswers == 3) {
            return time.plusWeeks(4);
        } else if (noOfSucessfulAnswers == 4) {
            return time.plusMonths(2);
        } else {
            return time.plusMonths(4);
        }
    }
}
