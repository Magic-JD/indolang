package main.rest.question;

import main.calculator.RepetitionDelayCalculator;
import main.database.mapper.WordTranslationsMapper;
import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import main.database.repository.LearnerCustomRepository;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Answer;
import main.rest.model.Result;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;

@Component
public class QuestionVerifierImpl implements QuestionVerifier {

    @Autowired private WordTranslationsRepository wordTranslationsRepository;
    @Autowired private WordTranslationsMapper mapper;
    @Autowired private LearnerRepository learnerRepository;
    @Autowired private LearnerCustomRepository learnerCustomRepository;
    @Autowired private RepetitionDelayCalculator repetitionDelayCalculator;

    @Override
    public Result verifyTest(String username, String language, Answer answer) {
        var translationsItem = retrieveTranslationItemForAskedQuestion(language, answer.getAskedQuestion());
        var result = calculateResult(answer, translationsItem.getTranslations());
        var learnerItem = retrieveLearnerItem(username, translationsItem.get_id());
        updateLearnerData(result, learnerItem);
        return result;
    }

    private DbWordTranslationsItem retrieveTranslationItemForAskedQuestion(String language, String askedQuestion) {
        return wordTranslationsRepository.findTranslationsFor(language, askedQuestion).orElseThrow(Exceptions.TranslationsNotFoundException::new);
    }

    private Result calculateResult(Answer answer, Set<String> correctTranslations) {
        if (answerFoundInCorrectTranslations(answer.getAnswer(), correctTranslations)) {
            return Result.createPassingResult(answer);
        } else {
            return Result.createFailingResult(answer, correctTranslations);
        }
    }

    private boolean answerFoundInCorrectTranslations(String answer, Set<String> correctTranslation) {
        return correctTranslation.stream().anyMatch(answer::equals);
    }

    private DbLearnerItem retrieveLearnerItem(String username, ObjectId translationItemId) {
        return learnerCustomRepository.findMatchingWord(username, translationItemId).orElseThrow(Exceptions.WordNotLearnedException::new);
    }

    private void updateLearnerData(Result result, DbLearnerItem learnerItem) {
        learnerItem.increaseOrResetSuccessfulAnswers(result.isPass());
        ZonedDateTime dateWithDelay = repetitionDelayCalculator.currentZDTWithDelayBeforeRetest(learnerItem.getSuccessfulAnswers());
        learnerItem.setDate(dateWithDelay);
        learnerRepository.save(learnerItem);
    }
}
