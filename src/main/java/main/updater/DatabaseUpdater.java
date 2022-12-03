package main.updater;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.updater.data.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdater {

    @Autowired WordTranslationsRepository repository;

    public void updateDatabase(Definition definition, String language) {
        DbWordTranslationsItem item = repository.findByKeyword(definition.getWord(), language)
                .orElse(new DbWordTranslationsItem(language, definition.getWord()));
        item.addToTranslations(definition.getTranslation());
        repository.save(item);
    }
}
