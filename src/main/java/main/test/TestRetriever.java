package main.test;

import main.wordset.WordData;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class TestRetriever {

    @Autowired
    WordsetCompiler wordsetCompiler;

    public String getEnglishWord() {
        return getValidWord(wordsetCompiler.getWordDataEnglish());
    }

    public String getIndonesianWord() {
        return getValidWord(wordsetCompiler.getWordDataEnglish());
    }

    private String getValidWord(List<WordData> wordDataList) {
        return wordDataList.stream()
                .filter(wordData -> wordData.getDate().isBefore(ZonedDateTime.now()))
                .findFirst()
                .map(WordData::getKeyWord)
                .orElse("Congratulations you have learned all the words");
    }

}
