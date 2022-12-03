package main.database.repository;

import main.database.model.DbLearnerItem;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class LearnerCustomRepositoryImpl implements LearnerCustomRepository {

    @Autowired MongoTemplate mongoTemplate;

    public Optional<DbLearnerItem> findMatchingWord(final String username, final ObjectId wordTranslationId) {
        var query = new Query(Criteria.where("username").is(username))
                .addCriteria(Criteria.where("wordTranslation").is(wordTranslationId));
        return Optional.ofNullable(mongoTemplate.findOne(query, DbLearnerItem.class));
    }

    @Override
    public Optional<DbLearnerItem> findNewestBeforeNow(String username, ZonedDateTime now) {
        var query = new Query(Criteria.where("username").is(username))
                .addCriteria(Criteria.where("date").lt(now))
                .with(Sort.by(Sort.Direction.DESC, "date"));
        return Optional.ofNullable(mongoTemplate.findOne(query, DbLearnerItem.class));
    }
}
