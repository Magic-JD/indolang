package main.database.mapper;

import main.database.model.DbWordTranslationsItem;
import main.rest.model.Definitions;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordTranslationsMapper {

    public List<Definitions> toDefinitionsList(List<DbWordTranslationsItem> items) {
        return items.stream().map(i -> new Definitions(i.getKeyWord(), i.getTranslations())).collect(Collectors.toList());
    }

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
