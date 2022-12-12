package main.database.model;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("word_translations")
@Getter
public class DbWordTranslationsItem {

    @Id
    private ObjectId _id = null;
    private final String locale;
    private final String keyWord;
    private Set<String> translations = new HashSet<>();

    public DbWordTranslationsItem(String locale, String keyWord) {
        this.locale = locale;
        this.keyWord = keyWord;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId id) {
        _id = id;
    }

    public void addToTranslations(String translation) {
        translations.add(translation);
    }

    public void removeFromTranslations(String translation) {
        translations.remove(translation);
    }

    public boolean containsTranslation(String translation) {
        return translations.contains(translation);
    }
}