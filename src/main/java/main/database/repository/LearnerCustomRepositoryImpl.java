package main.database.repository;

import main.database.model.DbLearnerItem;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class LearnerCustomRepositoryImpl implements LearnerCustomRepository {

    @Autowired MongoTemplate mongoTemplate;

    //@org.springframework.data.mongodb.repository.Query("{username: '?0', wordTranslation: '?1'}")
    public Optional<DbLearnerItem> findMatchingWord(final String username, final ObjectId wordTranslation) {
        Query query = new Query(Criteria.where("username").is(username))
                .addCriteria(Criteria.where("wordTranslation").is(wordTranslation));
        List<DbLearnerItem> items = mongoTemplate.find(query, DbLearnerItem.class);
        return items.stream().findAny();
    }

    @Override
    public Optional<DbLearnerItem> findNewestBeforeNow(String username, ZonedDateTime now) {
        Query query = new Query(Criteria.where("username").is(username))
                .addCriteria(Criteria.where("date").lt(now))
                .with(Sort.by(Sort.Direction.DESC, "date"))
                .with(PageRequest.of(1, 1));
        List<DbLearnerItem> items = mongoTemplate.find(query, DbLearnerItem.class);
        return items.stream().findAny();
    }
}
