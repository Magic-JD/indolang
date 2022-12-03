package main.database.repository;

import main.database.model.DbWordTranslationsItem;

import java.util.Optional;

public interface LearnerWordRepository {
    Optional<DbWordTranslationsItem> findNewQuestion(String username, String locale);

}
