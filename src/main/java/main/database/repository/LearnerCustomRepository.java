package main.database.repository;

import main.database.model.DbLearnerItem;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface LearnerCustomRepository {
    Optional<DbLearnerItem> findNewestBeforeNow(String username, ZonedDateTime now);

}
