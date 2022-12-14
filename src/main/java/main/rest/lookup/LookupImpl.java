package main.rest.lookup;

import main.database.mapper.WordTranslationsMapper;
import main.database.repository.WordTranslationsRepository;
import main.exception.Exceptions;
import main.rest.model.Definitions;
import main.rest.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LookupImpl implements Lookup {

    @Autowired private WordTranslationsRepository repository;
    @Autowired private WordTranslationsMapper mapper;

    @Override
    public Definitions lookupWord(String location, Word Word) {
        return repository.findTranslationsFor(location, Word.getWord())
                .map(mapper::toDefinitions)
                .orElseThrow(Exceptions.WordNotFoundException::new);
    }
}
