package main.wordset;

import main.database.model.DictionaryItem;
import main.database.repository.DictionaryRepository;
import main.lookup.data.Definitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordsetCompiler {

    private List<Definitions> englishToIndonesian;
    private List<Definitions> indonesianToEnglish;
    private List<WordData> wordDataEnglish;
    private List<WordData> wordDataIndonesian;

    @Autowired
    DictionaryRepository dicItemRep;

    @PostConstruct
    private void refreshFromDictionary() {
        Map<String, Set<String>> eTI = new HashMap<>();
        Map<String, Set<String>> iTE = new HashMap<>();
        List<DictionaryItem> all = dicItemRep.findAll();
        for (DictionaryItem d : all) {
            String en = d.getEnglishWord();
            String in = d.getIndonesianWord();
            populateMap(en, in, eTI);
            populateMap(in, en, iTE);
        }
        englishToIndonesian = sortedDefinitions(eTI);
        indonesianToEnglish = sortedDefinitions(iTE);
    }

    private void populateMap(String key, String value, Map<String, Set<String>> map) {
        if (map.containsKey(key)) {
            Set<String> set = map.get(key);
            set.add(value);
            map.put(key, set);
        } else {
            HashSet<String> set = new HashSet<>();
            set.add(value);
            map.put(key, set);
        }
    }

    private List<Definitions> sortedDefinitions(Map<String, Set<String>> map) {
        return map.entrySet().stream()
                .map(e -> new Definitions(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(Definitions::getWord))
                .collect(Collectors.toList());
    }

    public List<Definitions> getWordsetEnglishOrdered() {
        return englishToIndonesian;
    }

    public List<Definitions> getWordsetIndonesianOrdered() {
        return indonesianToEnglish;
    }

    public List<WordData> getWordDataIndonesian() {
        return getWordData(wordDataIndonesian, "INDONESIAN_WORDSET");
    }

    public List<WordData> getWordDataEnglish() {
        return getWordData(wordDataEnglish, "ENGLISH_WORDSET");
    }

    public List<WordData> getWordData(List<WordData> wordData, String location) {
        if (wordData == null) {
            wordData = getWordData(location);
        }
        return wordData;
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
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordDataCollection;
    }
}
