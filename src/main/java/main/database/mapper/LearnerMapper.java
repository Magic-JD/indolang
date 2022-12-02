package main.database.mapper;

import main.database.model.DbLearnerItem;
import main.database.model.DbWordTranslationsItem;
import org.springframework.stereotype.Component;

@Component
public class LearnerMapper {

    public DbWordTranslationsItem toWordItem(DbLearnerItem item) {
        return item.getWordTranslation();
    }
}
