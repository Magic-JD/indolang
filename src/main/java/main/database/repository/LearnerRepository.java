package main.database.repository;

import main.database.model.DbLearnerItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface LearnerRepository extends MongoRepository<DbLearnerItem, String> {
    @Query("{username: '?0', wordId: '?1'}")
    Optional<DbLearnerItem> findMatching(String username, ObjectId wordId);
}
