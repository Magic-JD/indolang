package main.updater;

import main.database.model.DictionaryItem;
import main.database.repository.DictionaryRepository;
import main.lookup.data.Definitions;
import main.updater.data.Definition;
import main.wordset.WordData;
import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static main.config.Constants.ENGLISH_WORDSET;
import static main.config.Constants.INDONESIAN_WORDSET;

@Component
public class UpdateFile {

    @Autowired
    WordsetCompiler compiler;

    @Autowired
    DictionaryRepository dicItemRep;


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
        long count = dicItemRep.count();
        for (String en : definition.getEnglishWords()) {
            for (String in : definition.getIndonesianWords()) {
                dicItemRep.save(new DictionaryItem(en, in));
            }
        }
        return dicItemRep.count() > count;
    }

    private List<WordData> updateWordDataFromDictionary(List<Definitions> wordset, List<WordData> wordData) {
        for (var def : wordset) {
            String oldWord = def.getWord();
            Set<String> translations = def.getWordDefinitions();
            Optional<WordData> matchingWordData = wordData.stream().filter(wd -> wd.getKeyWord().equals(oldWord)).findFirst();
            if (matchingWordData.isPresent()) {
                WordData existingWordData = matchingWordData.get();
                existingWordData.addTranslations(translations);
            } else {
                wordData.add(new WordData(oldWord, translations, ZonedDateTime.now(), 0));
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
