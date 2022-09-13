package main.updater;

import main.updater.data.Definition;
import main.wordset.WordData;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static main.config.Constants.*;

@Component
public class UpdateFile {

    @Autowired
    WordsetCompiler compiler;


    public void updateFileFromMemoryData() {
        var indonesianWordData = compiler.getWordDataIndonesian();
        var englishWordData = compiler.getWordDataEnglish();
        updateFileFromWordData(indonesianWordData, INDONESIAN_WORDSET);
        updateFileFromWordData(englishWordData, ENGLISH_WORDSET);
    }

    public void updateFromDictionaryFile() {
        var indonesianWordData = compiler.getWordDataIndonesian();
        var englishWordData = compiler.getWordDataEnglish();
        var wordsetIndonesianOrdered = compiler.getWordsetIndonesianOrdered();
        var wordsetEnglishOrdered = compiler.getWordsetEnglishOrdered();
        updateFileFromWordData(updateWordDataFromDictionary(wordsetIndonesianOrdered, indonesianWordData), INDONESIAN_WORDSET);
        updateFileFromWordData(updateWordDataFromDictionary(wordsetEnglishOrdered, englishWordData), ENGLISH_WORDSET);
    }

    public boolean addWordToDictionary(Definition definition) {
        try {
            var writer = new FileWriter(DICTIONARY_FILE, true);
            writer.write("\n" + String.join(":", definition.getEnglishWords()) + "," + String.join(":", definition.getIndonesianWords()));
            writer.flush();
            writer.close();
            compiler.refreshFromDictionary();
            updateFromDictionaryFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<WordData> updateWordDataFromDictionary(List<Pair<String, Set<String>>> wordset, List<WordData> wordData) {
        for (var pair : wordset) {
            String olWord = pair.getFirst();
            Set<String> translations = pair.getSecond();
            Optional<WordData> matchingWordData = wordData.stream().filter(wd -> wd.getKeyWord().equals(olWord)).findFirst();
            if (matchingWordData.isPresent()) {
                WordData existingWordData = matchingWordData.get();
                existingWordData.addTranslations(translations);
            } else {
                wordData.add(new WordData(olWord, translations, ZonedDateTime.now(), 0));
            }
        }
        return wordData;
    }

    private void updateFileFromWordData(List<WordData> wordset, String fileName) {
        var tempFileName = fileName.split("\\.")[0] + "_temp.txt";
        try {
            var writer = new FileWriter(tempFileName);
            for (var wordData : wordset) {
                writer.write(wordData.toString() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
        File originalFile = new File(fileName);
        File tempFile = new File(tempFileName);
        if (originalFile.delete()) {
            if (!tempFile.renameTo(new File(fileName))) {
                throw new RuntimeException("rename failed for " + fileName);
            }
        } else {
            throw new RuntimeException("delete failed for " + fileName);
        }
    }
}
