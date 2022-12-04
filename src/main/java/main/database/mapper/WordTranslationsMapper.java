package main.database.mapper;

import main.database.model.DbWordTranslationsItem;
import main.rest.model.Definitions;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WordTranslationsMapper {

    public Definitions toDefinitions(DbWordTranslationsItem item) {
        return new Definitions(item.getKeyWord(), item.getTranslations());
    }

    public Set<String> toTranslations(DbWordTranslationsItem item) {
        return item.getTranslations();
    }

    public String toWord(DbWordTranslationsItem item) {
        return item.getKeyWord();
    }

    public ObjectId toId(DbWordTranslationsItem item) {
        return item.get_id();
    }
}
