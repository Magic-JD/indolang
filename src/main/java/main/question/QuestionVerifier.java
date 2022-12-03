package main.question;

import main.database.mapper.WordTranslationsMapper;
import main.database.repository.LearnerCustomRepository;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.question.data.Answer;
import main.question.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;

@Component
public class QuestionVerifier {

    @Autowired
    private WordTranslationsRepository wordTranslationsRepository;
    @Autowired private WordTranslationsMapper mapper;
    @Autowired private LearnerRepository learnerRepository;
    @Autowired private LearnerCustomRepository learnerCustomRepository;

    public Result verifyTest(String username, Answer answer, String language) {
        var translationItem = wordTranslationsRepository.findTranslationsFor(answer.getAskedQuestion(), language);
        var translations = translationItem
                .map(mapper::toTranslations)
                .orElseThrow(Exceptions.TranslationsNotFoundException::new);
        var learnerItem = translationItem
                .map(mapper::toId)
                .flatMap(i -> learnerCustomRepository.findMatchingWord(username, i))
                .orElseThrow(Exceptions.UserHasNotLearnedWordException::new);
        var result = getResult(answer, translations);
        if (result.isPass()) {
            learnerItem.increaseSuccessfulAnswers();
        } else {
            learnerItem.resetSuccessfulAnswers();
        }
        learnerItem.setDate(calulateDate(learnerItem.getSuccessfulAnswers()));
        learnerRepository.save(learnerItem);
        return result;
    }

    private static Result getResult(Answer answer, Set<String> strings) {
        return strings.stream()
                .filter(t -> t.equals(answer.getAnswer()))
                .findAny().map(s -> new Result(true, String.format("Correct, the translation of: '%s' is: '%s'.",
                        answer.getAskedQuestion(),
                        answer.getAnswer())))
                .orElse(new Result(false, String.format("Unfortunately '%s' is not the correct translation of '%s'. You should have chosen one of %s.",
                        answer.getAnswer(),
                        answer.getAskedQuestion(),
                        String.join(", ", strings))));
    }

    private ZonedDateTime calulateDate(int noOfSucessfulAnswers) {
        var time = ZonedDateTime.now();
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
