package main.database.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("word_translations")
@Data
public class DbWordTranslationsItem {
    private final String locale;
    private final String keyWord;
    private final Set<String> translations;
}