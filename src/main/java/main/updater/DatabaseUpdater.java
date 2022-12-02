package main.updater;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.updater.data.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseUpdater {

    @Autowired
    WordTranslationsRepository repository;

    public boolean updateDatabase(Definition definition, String language) {
        return repository.findByKeyword(definition.getWord(), language)
                .map(item -> updateTranslations(item, definition.getTranslation()))
                .map(repository::save)
                .isPresent();
    }

    private DbWordTranslationsItem updateTranslations(DbWordTranslationsItem item, String newWord) {
        Set<String> translations = item.getTranslations();
        translations.add(newWord);
        item.setTranslations(translations);
        return item;
    }
}
