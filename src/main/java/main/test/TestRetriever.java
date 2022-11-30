package main.test;

import main.lookup.data.Word;
import main.wordset.WordData;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TestRetriever {

    @Autowired
    WordsetCompiler wordsetCompiler;

    public Optional<Word> getEnglishWord() {
        return getValidWord(wordsetCompiler.getWordDataEnglish());
    }

    public Optional<Word> getIndonesianWord() {
        return getValidWord(wordsetCompiler.getWordDataIndonesian());
    }

    private Optional<Word> getValidWord(List<WordData> wordDataList) {
        return wordDataList.stream()
                .filter(wordData -> wordData.getDate().isBefore(ZonedDateTime.now()))
                .findFirst()
                .map(WordData::getKeyWord)
                .map(Word::new);
    }

}
