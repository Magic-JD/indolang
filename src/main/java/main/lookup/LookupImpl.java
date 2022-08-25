package main.lookup;

import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LookupImpl implements Lookup {

    @Autowired WordsetCompiler wordsetCompiler;

    public LookupImpl(){}

    @Override
    public String lookupIndonesianWord(String englishWord){
        return wordFinder(wordsetCompiler.getWordsetEnglishOrdered(), englishWord);
    }

    @Override
    public String lookupEnglishWord(String indonesianWord){
        return wordFinder(wordsetCompiler.getWordsetIndonesianOrdered(), indonesianWord);

    }

    private String wordFinder(List<Pair<String, String>> wordset, String word){
        return wordset.stream()
                .filter(pair -> pair.getFirst().equals(word))
                .findFirst()
                .map(Pair::getSecond)
                .orElse("Kata ini gak ada");
    }
}
