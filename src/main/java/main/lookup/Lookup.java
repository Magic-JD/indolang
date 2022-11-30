package main.lookup;

import main.lookup.data.Definitions;
import main.lookup.data.Word;

import java.util.Optional;

public interface Lookup {

    Optional<Definitions> lookupIndonesianWord(Word englishWord);

    Optional<Definitions> lookupEnglishWord(Word indonesianWord);

}
