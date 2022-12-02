package main.database.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("word_translations")
@Data
@Getter
public class DbWordTranslationsItem {
    @Id
    private final String _id;
    private final String locale;
    private final String keyWord;
    private Set<String> translations = new HashSet<>();

    public void addToTranslations(String translation) {
        this.translations.add(translation);
    }
}