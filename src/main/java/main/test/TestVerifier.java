package main.test;

import main.test.data.Answer;
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

    public String verifyTestEnglish(Answer answer) {
        return verifyTest(wordsetCompiler.getWordDataEnglish(), answer);
    }

    public String verifyTestIndonesian(Answer answer) {
        return verifyTest(wordsetCompiler.getWordDataIndonesian(), answer);
    }

    private String verifyTest(List<WordData> wordDataList, Answer answer) {
        Optional<WordData> matchingData = wordDataList.stream().filter(wordData -> wordData.getKeyWord().equals(answer.getAskedQuestion())).findFirst();
        return matchingData.map(data -> {
            if (data.getTranslations().contains(answer.getAnswer())) {
                data.setSucessfulAnswers(data.getSucessfulAnswers() + 1);
                data.setDate(calulateDate(data.getSucessfulAnswers()));
                return "Correct, the translation of: '" + answer.getAskedQuestion() + "' is: '" + answer.getAnswer() + "'";
            } else {
                data.setSucessfulAnswers(0);
                data.setDate(ZonedDateTime.now());
                return "Unfortunately '" + answer.getAnswer() + "' is not the correct translation of '" + answer.getAskedQuestion() + "'. You should have chosen one of " + String.join(", ", data.getTranslations()) + ".";
            }
        }).orElse("Couldn't find the keyword given.");
    }

    private ZonedDateTime calulateDate(int noOfSucessfulAnswers) {
        ZonedDateTime time = ZonedDateTime.now();
        if (noOfSucessfulAnswers == 0) {
            return time;
        } else if (noOfSucessfulAnswers == 1) {
            return time.plusMinutes(20);
        } else if (noOfSucessfulAnswers == 2) {
            return time.plusHours(4);
        } else if (noOfSucessfulAnswers == 3) {
            return time.plusDays(4);
        } else if (noOfSucessfulAnswers == 4) {
            return time.plusWeeks(4);
        } else {
            return time.plusMonths(4);
        }
    }
}
