package main.database.repository;

import main.database.model.DbWordTranslationsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LearnerWordRepositoryImpl implements LearnerWordRepository {

    @Autowired LearnerRepository learnerRepository;
    @Autowired MongoTemplate mongoTemplate;

    @Override
    public Optional<DbWordTranslationsItem> findNewQuestion(String username, String language) {
        var objectIdStream = learnerRepository.findAllForLearner(username).stream().map(dbl -> dbl.getWordTranslation().get_id());
        var query = new Query(Criteria.where("locale").is(language).and("_id").nin(objectIdStream));
        return Optional.ofNullable(mongoTemplate.findOne(query, DbWordTranslationsItem.class));
    }
}
