package main.dictionary;

import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DictionaryImpl implements Dictionary {

    @Autowired
    WordsetCompiler wordsetCompiler;

    public DictionaryImpl() {
    }

    @Override
    public String englishToIndonesian() {
        return convertToString(wordsetCompiler.getWordsetEnglishOrdered());
    }

    @Override
    public String indonesianToEnglish() {
        return convertToString(wordsetCompiler.getWordsetIndonesianOrdered());

    }

    private String convertToString(List<Pair<String, Set<String>>> list) {
        return list.stream()
                .map(pair -> String.format("%s = %s\n", pair.getFirst(), String.join("/", pair.getSecond())))
                .collect(Collectors.joining());
    }
}
