package main.rest.updater;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdaterImpl implements DatabaseUpdater {

    @Autowired private WordTranslationsRepository repository;

    @Override
    public void updateDatabase(String language, Definition definition) {
        var item = repository.findByKeyword(language, definition.getWord())
                .orElse(new DbWordTranslationsItem(language, definition.getWord()));
        item.addToTranslations(definition.getTranslation());
        repository.save(item);
    }

    @Override
    public void removeFromDatabase(String language, Definition definition) {
        DbWordTranslationsItem item = repository.findByKeyword(language, definition.getWord())
                .filter(i -> i.containsTranslation(definition.getTranslation()))
                .orElseThrow(Exceptions.WordDoesNotExistToBeRemoved::new);
        updateOrDelete(item, definition.getTranslation());
    }

    private void updateOrDelete(DbWordTranslationsItem item, String translation) {
        item.removeFromTranslations(translation);
        if (item.getTranslations().isEmpty()) {
            repository.delete(item);
        } else {
            repository.save(item);
        }
    }
}
