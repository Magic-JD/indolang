package main.wordset;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordsetCompiler {

    private static final String indonesiaFile = "src/main/resources/indonesian_file.txt"; //Autoload this properly from properties later
    private final Map<String, Set<String>> englishToIndonesian;
    private final Map<String, Set<String>> indonesianToEnglish;


    public WordsetCompiler() {
        englishToIndonesian = new TreeMap<>();
        indonesianToEnglish = new TreeMap<>();
        try {
            File file = new File(indonesiaFile);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String translation = scanner.nextLine();
                String[] info = translation.split(",");
                String[] englishWords = info[0].split(":");
                String[] indonesianWords = info[1].split(":");
                for (String englishWord : englishWords) {
                    englishToIndonesian.put(englishWord, Arrays.stream(indonesianWords).collect(Collectors.toSet()));
                }
                for (String indonesianWord : indonesianWords) {
                    indonesianToEnglish.put(indonesianWord, Arrays.stream(englishWords).collect(Collectors.toSet()));
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
}
