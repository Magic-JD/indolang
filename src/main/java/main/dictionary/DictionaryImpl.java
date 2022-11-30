package main.dictionary;

import main.lookup.data.Definitions;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryImpl implements Dictionary {

    @Autowired
    WordsetCompiler wordsetCompiler;

    @Override
    public List<Definitions> englishToIndonesian() {
        return wordsetCompiler.getWordsetEnglishOrdered();
    }

    @Override
    public List<Definitions> indonesianToEnglish() {
        return wordsetCompiler.getWordsetIndonesianOrdered();

    }

}
