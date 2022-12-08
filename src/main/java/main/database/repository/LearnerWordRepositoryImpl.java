package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class LearnerWordRepositoryImpl implements LearnerWordRepository {

    @Autowired private LearnerRepository learnerRepository;
    @Autowired private MongoTemplate mongoTemplate;

    @Override
    public Optional<DbWordTranslationsItem> findNewQuestion(String username, String language) {
        var alreadyLearntWordIds = learnerRepository.findAllForLearner(username).stream()
                .map(learnerItem -> learnerItem.getWordTranslation().get_id())
                .collect(toList());
        var query = new Query(where("locale").is(language));
        if (!alreadyLearntWordIds.isEmpty()) {
            query.addCriteria(where("_id").nin(alreadyLearntWordIds));
        }
        return Optional.ofNullable(mongoTemplate.findOne(query, DbWordTranslationsItem.class));
    }
}
