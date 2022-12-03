package main.rest.dictionary;

import main.database.mapper.WordTranslationsMapper;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.Definitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryImpl implements Dictionary {

    @Autowired WordTranslationsRepository wordTranslationsRepository;
    @Autowired WordTranslationsMapper mapper;

    @Override
    public List<Definitions> wordsToTranslations(String language) {
        return mapper.toDefinitionsList(wordTranslationsRepository.findAllFrom(language));
    }
}
