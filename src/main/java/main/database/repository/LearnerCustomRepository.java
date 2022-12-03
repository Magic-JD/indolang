package main.database.repository;

import main.database.model.DbLearnerItem;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface LearnerCustomRepository {
    Optional<DbLearnerItem> findNewestBeforeNow(String username, ZonedDateTime now);
    Optional<DbLearnerItem> findMatchingWord(final String username, final ObjectId wordTranslation);
}
