package main.rest.question;

import main.database.mapper.LearnerMapper;
import main.database.mapper.WordTranslationsMapper;
import main.database.model.DbLearnerItem;
import main.database.repository.LearnerCustomRepository;
import main.database.repository.LearnerRepository;
import main.database.repository.LearnerWordRepository;
import main.rest.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class QuestionRetrieverImpl implements QuestionRetriever {

    @Autowired LearnerRepository learnerRepository;
    @Autowired LearnerCustomRepository learnerCustomRepository;
    @Autowired LearnerMapper learnerMapper;
    @Autowired LearnerWordRepository learnerWordRepository;
    @Autowired WordTranslationsMapper wordTranslationsMapper;

    @Override
    public Optional<Word> getWord(String username, String language) {
        return learnerCustomRepository
                .findNewestBeforeNow(username, ZonedDateTime.now())
                .map(learnerMapper::toWordItem)
                .map(wordTranslationsMapper::toWord)
                .map(Word::new).or(() -> getNewQuestion(username, language));
    }

    private Optional<Word> getNewQuestion(String username, String language) {
        var newQuestion = learnerWordRepository.findNewQuestion(username, language);
        newQuestion.ifPresent(translationItem -> learnerRepository.save(new DbLearnerItem(translationItem, username, ZonedDateTime.now(), 0)));
        return newQuestion.map(wordTranslationsMapper::toWord).map(Word::new);
    }
}
