package main.rest.lookup;

import main.rest.model.Definitions;
import main.rest.model.Word;

import java.util.Optional;

public interface Lookup {
    Optional<Definitions> lookupWord(String language, Word word);
}
