package main.lookup;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class LookupImpl implements Lookup {

    private final Map<String, String> englishToIndonesian;
    String indonesiaFile = "src/main/resources/indonesian_file.txt"; //Autoload this properly from properties later

    public LookupImpl(){
        englishToIndonesian = new HashMap<>();
        try {
            File file = new File(indonesiaFile);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String indonesianLine = scanner.nextLine();
                String[] info = indonesianLine.split(",");
                englishToIndonesian.put(info[0], info[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String lookupIndonesianWord(String englishWord){
        return englishToIndonesian.getOrDefault(englishWord, "Kata ini gak ada");
    }
}
