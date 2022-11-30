package main.lookup;

import main.lookup.data.Definitions;
import main.lookup.data.Word;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LookupImpl implements Lookup {

    @Autowired WordsetCompiler wordsetCompiler;

    @Override
    public Optional<Definitions> lookupIndonesianWord(Word englishWord) {
        return wordFinder(wordsetCompiler.getWordsetEnglishOrdered(), englishWord);
    }

    @Override
    public Optional<Definitions> lookupEnglishWord(Word indonesianWord) {
        return wordFinder(wordsetCompiler.getWordsetIndonesianOrdered(), indonesianWord);

    }

    private Optional<Definitions> wordFinder(List<Definitions> wordset, Word word) {
        return wordset.stream()
                .filter(def -> def.getWord().equals(word.getWord()))
                .findFirst();
    }
}
