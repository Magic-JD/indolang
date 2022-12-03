package main.rest.updater;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdaterImpl implements DatabaseUpdater {

    @Autowired WordTranslationsRepository repository;

    @Override
    public void updateDatabase(Definition definition, String language) {
        DbWordTranslationsItem item = repository.findByKeyword(definition.getWord(), language)
                .orElse(new DbWordTranslationsItem(language, definition.getWord()));
        item.addToTranslations(definition.getTranslation());
        repository.save(item);
    }
}
