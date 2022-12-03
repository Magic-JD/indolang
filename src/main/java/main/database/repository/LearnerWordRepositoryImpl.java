package main.database.repository;

import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LearnerWordRepositoryImpl implements LearnerWordRepository {

    @Autowired WordTranslationsRepository wordTranslationsRepository;
    @Autowired LearnerRepository learnerRepository;

    //TODO this properly
    @Override
    public Optional<DbWordTranslationsItem> findNewQuestion(String username, String language) {
        List<DbWordTranslationsItem> wordTranslationsItems = wordTranslationsRepository.findAllFrom(language);
        List<DbWordTranslationsItem> learnerItems = learnerRepository.findAllForLearner(username)
                .stream()
                .map(DbLearnerItem::getWordTranslation)
                .collect(Collectors.toList());
        return wordTranslationsItems.stream().filter(item -> !learnerItems.contains(item)).findAny();
    }
}
