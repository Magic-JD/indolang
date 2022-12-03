package main.rest.lookup;

import main.rest.lookup.data.Definitions;
import main.rest.lookup.data.Word;

import java.util.Optional;

public interface Lookup {
    Optional<Definitions> lookupWord(String language, Word word);
}
