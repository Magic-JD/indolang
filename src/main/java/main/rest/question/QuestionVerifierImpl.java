package main.rest.question;

import main.database.mapper.WordTranslationsMapper;
import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import main.database.repository.LearnerCustomRepository;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.question.data.Answer;
import main.rest.question.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class QuestionVerifierImpl implements QuestionVerifier {

    @Autowired
    private WordTranslationsRepository wordTranslationsRepository;
    @Autowired private WordTranslationsMapper mapper;
    @Autowired private LearnerRepository learnerRepository;
    @Autowired private LearnerCustomRepository learnerCustomRepository;

    @Override
    public Result verifyTest(String username, Answer answer, String language) {
        var translationItem = wordTranslationsRepository.findTranslationsFor(answer.getAskedQuestion(), language);
        var translations = getTranslationsSet(translationItem);
        var learnerItem = retrieveLearnerItem(username, translationItem);
        var result = getResult(answer, translations);
        learnerItem.updateSuccessfulAnswers(result.isPass());
        learnerItem.setDate(calculateDate(learnerItem.getSuccessfulAnswers()));
        learnerRepository.save(learnerItem);
        return result;
    }

    private Set<String> getTranslationsSet(Optional<DbWordTranslationsItem> translationItem) {
        return translationItem
                .map(mapper::toTranslations)
                .orElseThrow(Exceptions.TranslationsNotFoundException::new);
    }

    private DbLearnerItem retrieveLearnerItem(String username, Optional<DbWordTranslationsItem> translationItem) {
        return translationItem
                .map(mapper::toId)
                .flatMap(id -> learnerCustomRepository.findMatchingWord(username, id))
                .orElseThrow(Exceptions.UserHasNotLearnedWordException::new);
    }

    //TODO make front end make the sentence
    private Result getResult(Answer answer, Set<String> strings) {
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

    private ZonedDateTime calculateDate(int noOfSuccessfulAnswers) {
        var time = ZonedDateTime.now();
        if (noOfSuccessfulAnswers == 0) {
            return time.plusMinutes(5);
        } else if (noOfSuccessfulAnswers == 1) {
            return time.plusHours(4);
        } else if (noOfSuccessfulAnswers == 2) {
            return time.plusDays(4);
        } else if (noOfSuccessfulAnswers == 3) {
            return time.plusWeeks(4);
        } else if (noOfSuccessfulAnswers == 4) {
            return time.plusMonths(2);
        } else {
            return time.plusMonths(4);
        }
    }
}
