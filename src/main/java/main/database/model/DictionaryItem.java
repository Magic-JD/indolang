package main.database.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Deprecated
@Document("dictionary")
@Data
public class DictionaryItem {
    private final String englishWord;
    private final String indonesianWord;
}