package main.test;

import main.database.mapper.LearnerMapper;
import main.database.mapper.WordTranslationsMapper;
import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import main.database.repository.LearnerRepository;
import main.database.repository.WordTranslationsRepository;
import main.lookup.data.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class TestRetriever {

    @Autowired
    LearnerRepository learnerRepository;
    @Autowired
    LearnerMapper learnerMapper;
    @Autowired
    WordTranslationsRepository wordTranslationsRepository;
    @Autowired
    WordTranslationsMapper wordTranslationsMapper;

    public Optional<Word> getWord(String username, String language) {
        Optional<String> newestBeforeNow = learnerRepository.findNewestBeforeNow(username, ZonedDateTime.now()).map(learnerMapper::toWordId);
        if (newestBeforeNow.isEmpty()) {
            Optional<DbWordTranslationsItem> newQuestion = wordTranslationsRepository.findNewQuestion(language, username);
            newQuestion.ifPresent(translationItem -> learnerRepository.save(new DbLearnerItem(null, translationItem.get_id(), username, ZonedDateTime.now(), 0)));
            return newQuestion.map(wordTranslationsMapper::toWord).map(Word::new);

        } else {
            return newestBeforeNow
                    .flatMap(wordTranslationsRepository::findById)
                    .map(wordTranslationsMapper::toWord)
                    .map(Word::new);
        }
    }
}
