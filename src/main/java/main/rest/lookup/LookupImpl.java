package main.rest.lookup;

import main.database.mapper.WordTranslationsMapper;
import main.database.repository.WordTranslationsRepository;
import main.rest.model.Definitions;
import main.rest.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LookupImpl implements Lookup {

    @Autowired WordTranslationsRepository repository;
    @Autowired WordTranslationsMapper mapper;

    @Override
    public Optional<Definitions> lookupWord(String location, Word Word) {
        return repository.findTranslationsFor(Word.getWord(), location).map(mapper::toDefinitions);
    }
}
