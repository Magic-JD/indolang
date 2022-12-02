package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordTranslationsRepository extends MongoRepository<DbWordTranslationsItem, String> {


}
