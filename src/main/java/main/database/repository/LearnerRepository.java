package main.database.repository;

import main.database.model.DbLearnerItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LearnerRepository extends MongoRepository<DbLearnerItem, String> {

    @Query("{username: '?0'}")
    List<DbLearnerItem> findAllForLearner(String username);
}
