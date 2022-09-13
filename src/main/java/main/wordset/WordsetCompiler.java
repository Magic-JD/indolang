package main.wordset;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static main.config.Constants.*;

@Component
public class WordsetCompiler {

    private final Map<String, Set<String>> englishToIndonesian;
    private final Map<String, Set<String>> indonesianToEnglish;
    private List<WordData> wordDataEnglish;
    private List<WordData> wordDataIndonesian;


    public WordsetCompiler() {
        englishToIndonesian = new TreeMap<>();
        indonesianToEnglish = new TreeMap<>();
        refreshFromDictionary();
    }

    public void refreshFromDictionary() {
        try {
            File file = new File(DICTIONARY_FILE);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String translation = scanner.nextLine();
                String[] info = translation.split(",");
                Set<String> englishWords = Arrays.stream(info[0].split(":")).map(String::toLowerCase).collect(Collectors.toSet());
                Set<String> indonesianWords = Arrays.stream(info[1].split(":")).map(String::toLowerCase).collect(Collectors.toSet());
                for (String englishWord : englishWords) {
                    englishToIndonesian.put(englishWord, indonesianWords);
                }
                for (String indonesianWord : indonesianWords) {
                    indonesianToEnglish.put(indonesianWord, englishWords);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Pair<String, Set<String>>> getWordsetEnglishOrdered() {
        var orderedEnglishWordset = new ArrayList<Pair<String, Set<String>>>();
        englishToIndonesian.forEach((a, b) -> orderedEnglishWordset.add(Pair.of(a, b)));
        return orderedEnglishWordset;
    }

    public List<Pair<String, Set<String>>> getWordsetIndonesianOrdered() {
        var orderedIndonesianWordset = new ArrayList<Pair<String, Set<String>>>();
        indonesianToEnglish.forEach((a, b) -> orderedIndonesianWordset.add(Pair.of(a, b)));
        return orderedIndonesianWordset;
    }

    public List<WordData> getWordDataIndonesian() {
        if (wordDataIndonesian == null) {
            wordDataIndonesian = getWordData(INDONESIAN_WORDSET);
        }
        return wordDataIndonesian;
    }

    public List<WordData> getWordDataEnglish() {
        if (wordDataEnglish == null) {
            wordDataEnglish = getWordData(ENGLISH_WORDSET);
        }
        return wordDataEnglish;
    }

    private List<WordData> getWordData(String fileName) {
        var wordDataCollection = new ArrayList<WordData>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String wordDataLine = scanner.nextLine();
                String[] data = wordDataLine.split(",");
                wordDataCollection.add(new WordData(data[0], Arrays.stream(data[1].split(":")).collect(Collectors.toSet()), ZonedDateTime.parse(data[2]), Integer.valueOf(data[3])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordDataCollection;
    }
}
