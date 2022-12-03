package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordTranslationsRepository extends MongoRepository<DbWordTranslationsItem, String> {
    @Query("{locale:'?0'}")
    List<DbWordTranslationsItem> findAllFrom(String locale);

    @Query(value = "{keyWord: '?0', locale: '?1'}", fields = "{'keyWord' : 1, 'translations' : 1}")
    Optional<DbWordTranslationsItem> findTranslationsFor(String keyWord, String locale);

    @Query("{keyWord: '?0', locale: '?1'}")
    Optional<DbWordTranslationsItem> findByKeyword(String keyWord, String locale);
}
