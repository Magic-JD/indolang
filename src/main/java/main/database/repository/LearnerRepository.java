package main.database.repository;

import main.database.model.DbLearnerItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LearnerRepository extends MongoRepository<DbLearnerItem, String> {
    @Query("{username: '?0', wordTranslation: '?1'}")
    Optional<DbLearnerItem> findMatchingWord(String username, ObjectId wordTranslation);

    @Query("{username: '?0'}")
    List<DbLearnerItem> findAllForLearner(String username);
}
