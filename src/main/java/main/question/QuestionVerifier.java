package main.question;

import main.database.mapper.WordTranslationsMapper;
import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.question.data.Answer;
import main.question.data.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class QuestionVerifier {

    @Autowired
    private WordTranslationsRepository wordTranslationsRepository;
    @Autowired
    private WordTranslationsMapper mapper;
    @Autowired
    private LearnerRepository learnerRepository;


    public Result verifyTest(String username, Answer answer, String language) {
        Optional<DbWordTranslationsItem> translationItem = wordTranslationsRepository.findTranslationsFor(answer.getAskedQuestion(), language);
        Optional<Set<String>> translations = translationItem.map(mapper::toTranslations);
        Optional<ObjectId> id = translationItem.map(mapper::toId);
        //TODO better error handling here
        Set<String> strings = translations.orElseThrow();
        //TODO better error handling here
        DbLearnerItem item = id.flatMap(i -> learnerRepository.findMatchingWords(username, i)).orElseThrow();
        Result result = getResult(answer, strings);
        if (result.isPass()) {
            item.setSuccessfulAnswers(item.getSuccessfulAnswers() + 1);
        } else {
            item.setSuccessfulAnswers(0);
        }
        item.setDate(calulateDate(item.getSuccessfulAnswers()));
        learnerRepository.save(item);
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
