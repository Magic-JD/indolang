package main.database.mapper;

import main.database.model.DbLearnerItem;
import org.springframework.stereotype.Component;

@Component
public class LearnerMapper {

    public String toWordId(DbLearnerItem item) {
        return item.getWordId();
    }
}
