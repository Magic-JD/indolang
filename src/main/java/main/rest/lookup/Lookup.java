package main.rest.lookup;

import main.rest.model.Definitions;
import main.rest.model.Word;

public interface Lookup {
    Definitions lookupWord(String language, Word word);
}
