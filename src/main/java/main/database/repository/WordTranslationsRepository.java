package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordTranslationsRepository extends MongoRepository<DbWordTranslationsItem, String> {

    @Query(value = "{locale:'?0'}", fields = "{'keyWord' : 1, 'translations' : 1}")
    List<DbWordTranslationsItem> findAllFrom(String locale);

    //TODO Needs a query to find any one that isn't featured for the learner
    @Query(value = "{locale:'?0'}", fields = "{'keyWord' : 1, 'translations' : 1}")
    Optional<DbWordTranslationsItem> findNewQuestion(String locale, String username);

    @Query(value = "{keyWord:'?0', locale:'?0'}", fields = "{'translations' : 1}")
    Optional<DbWordTranslationsItem> findTranslationsFor(String keyWord, String locale);

}
