package main.updater;

import main.wordset.WordsetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Component
public class UpdateFromFile {

    @Autowired
    WordsetCompiler compiler;
    private static final String INDONESIAN_WORDSET = "src/main/resources/indonesia_file.txt"; //Autoload this properly from properties later
    private static final String ENGLISH_WORDSET = "src/main/resources/english_file.txt"; //Autoload this properly from properties later


    public void update() {
        var wordsetIndonesianOrdered = compiler.getWordsetIndonesianOrdered();
        var wordsetEnglishOrdered = compiler.getWordsetEnglishOrdered();
        writeToFile(wordsetIndonesianOrdered, INDONESIAN_WORDSET);
        writeToFile(wordsetEnglishOrdered, ENGLISH_WORDSET);
    }

    private void writeToFile(List<Pair<String, Set<String>>> wordset, String fileName) {
        try {
            var writer = new FileWriter(fileName);
            for (var pair : wordset) {
                writer.write(pair.getFirst() + "," + String.join(":", pair.getSecond()) + "," + ZonedDateTime.now() + ",0\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
