package main.database.repository;

import main.database.model.DbLearnerItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface LearnerRepository extends MongoRepository<DbLearnerItem, String> {

    //TODO work out how to do this correctly.
    @Query(value = "{locale:'?0', ???}", fields = "{'keyWord' : 1, 'translations' : 1}")
    Optional<DbLearnerItem> findNewestBeforeNow(String username, ZonedDateTime time);

    @Query(value = "{username: '?0', wordId: '?0'}")
    Optional<DbLearnerItem> findMatching(String username, String wordId);
}
