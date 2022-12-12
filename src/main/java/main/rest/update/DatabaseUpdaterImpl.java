package main.rest.update;

import main.database.model.DbWordTranslationsItem;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdaterImpl implements DatabaseUpdater {

    @Autowired private WordTranslationsRepository wordTranslationRepository;

    @Override
    public void updateDatabase(String language, Definition definition) {
        var item = wordTranslationRepository.findByKeyword(language, definition.getWord())
                .orElse(new DbWordTranslationsItem(language, definition.getWord()));
        item.addToTranslations(definition.getTranslation());
        wordTranslationRepository.save(item);
    }

    @Override
    public void removeFromDatabase(String language, Definition definition) {
        var translationThatShouldBeRemoved = definition.getTranslation();
        DbWordTranslationsItem wordTranslationsItem = wordTranslationRepository.findByKeyword(language, definition.getWord())
                .filter(item -> item.containsTranslation(translationThatShouldBeRemoved))
                .orElseThrow(Exceptions.WordDoesNotExistToBeRemoved::new);
        updateOrDelete(wordTranslationsItem, translationThatShouldBeRemoved);
    }

    private void updateOrDelete(DbWordTranslationsItem item, String translation) {
        item.removeFromTranslations(translation);
        if (item.getTranslations().isEmpty()) {
            wordTranslationRepository.delete(item);
        } else {
            wordTranslationRepository.save(item);
        }
    }
}
