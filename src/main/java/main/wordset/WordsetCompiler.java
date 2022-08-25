package main.wordset;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.util.*;
import java.util.function.BiConsumer;

@Component
public class WordsetCompiler {

    private static final String indonesiaFile = "src/main/resources/indonesian_file.txt"; //Autoload this properly from properties later
    private final Map<String, String> englishToIndonesian;
    private final Map<String, String> indonesianToEnglish;


    public WordsetCompiler(){
        englishToIndonesian = new TreeMap<>();
        indonesianToEnglish = new TreeMap<>();
        try {
            File file = new File(indonesiaFile);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String translation = scanner.nextLine();
                String[] info = translation.split(",");
                englishToIndonesian.put(info[0], info[1]);
                indonesianToEnglish.put(info[1], info[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Pair<String, String>> getWordsetEnglishOrdered() {
        var orderedEnglishWordset = new ArrayList<Pair<String, String>>();
        englishToIndonesian.forEach((a, b) -> orderedEnglishWordset.add(Pair.of(a,b)));
        return orderedEnglishWordset;
    }

    public List<Pair<String, String>> getWordsetIndonesianOrdered() {
        var orderedIndonesianWordset = new ArrayList<Pair<String, String>>();
        indonesianToEnglish.forEach((a, b) -> orderedIndonesianWordset.add(Pair.of(a,b)));
        return orderedIndonesianWordset;
    }
}
