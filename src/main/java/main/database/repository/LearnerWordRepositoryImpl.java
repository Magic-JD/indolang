package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LearnerWordRepositoryImpl implements LearnerWordRepository {

    @Autowired private LearnerRepository learnerRepository;
    @Autowired private MongoTemplate mongoTemplate;

    @Override
    public Optional<DbWordTranslationsItem> findNewQuestion(String username, String language) {
        var objectIds = learnerRepository.findAllForLearner(username).stream()
                .map(dbl -> dbl.getWordTranslation().get_id())
                .collect(Collectors.toList());
        var query = new Query(Criteria.where("locale").is(language));
        if (!objectIds.isEmpty()) {
            query.addCriteria(Criteria.where("_id").nin(objectIds));
        }
        return Optional.ofNullable(mongoTemplate.findOne(query, DbWordTranslationsItem.class));
    }
}
