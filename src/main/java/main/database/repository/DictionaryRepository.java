package main.database.repository;

import main.database.model.DictionaryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DictionaryRepository extends MongoRepository<DictionaryItem, String> {

    @Query(value = "{englishWord:'?0'}", fields = "{'indonesianWord' : 1}")
    List<DictionaryItem> findAll(String englishWord);

    public long count();
}
