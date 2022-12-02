package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LearnerWordRepositoryImpl implements LearnerWordRepository {

    @Autowired MongoTemplate mongoTemplate;

    @Override
    public Optional<DbWordTranslationsItem> findNewQuestion(String locale, String username) {
        return Optional.empty();
    }
}
