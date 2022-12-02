package main.lookup;

import main.lookup.data.Definitions;
import main.lookup.data.Word;

import java.util.Optional;

public interface Lookup {

    Optional<Definitions> lookupWord(String language, Word word);
}
