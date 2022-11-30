package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class DataManipulator {

    public static final String DICTIONARY_FILE = "src/main/resources/dictionary_file.txt"; //Autoload this properly from properties later


    public static void main(String... args) {
        testDatabase();
    }

    private static void retrieveAllEnglishWords() {
        Set<String> englishWords = new HashSet<>();
        try {
            File file = new File(DICTIONARY_FILE);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String wordDataLine = scanner.nextLine();
                String[] data = wordDataLine.split(",");
                String[] englishData = data[0].split(":");
                englishWords.addAll(Arrays.stream(englishData).map(String::toLowerCase).collect(Collectors.toSet()));
            }
            scanner.close();
            File file2 = new File("src/main/resources/dictionary_file_english_output.txt");
            FileWriter fileWriter = new FileWriter(file2);
            for (String word : englishWords) {
                fileWriter.write(word + ",\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void retrieveAllIndonesianWords() {
        Set<String> englishWords = new HashSet<>();
        try {
            File file = new File(DICTIONARY_FILE);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String wordDataLine = scanner.nextLine();
                String[] data = wordDataLine.split(",");
                String[] englishData = data[1].split(":");
                englishWords.addAll(Arrays.stream(englishData).map(String::toLowerCase).collect(Collectors.toSet()));
            }
            scanner.close();
            File file2 = new File("src/main/resources/dictionary_file_indonesian_output.txt");
            FileWriter fileWriter = new FileWriter(file2);
            for (String word : englishWords) {
                fileWriter.write(word + ",\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDatabase() {
//        ConnectionString connectionString = new ConnectionString("mongodb+srv://indolang:<password>@cluster0.tkyopm7.mongodb.net/?retryWrites=true&w=majority");
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .serverApi(ServerApi.builder()
//                        .version(ServerApiVersion.V1)
//                        .build())
//                .build();
//        MongoClient mongoClient = MongoClients.create(settings);
//        //Connecting to the database
//        MongoDatabase database = mongo.getDatabase("INDOLANG");
//        //Creating a collection object
//        MongoCollection<Document> collection = database.getCollection("INDONESIAN_WORD");
        // find code goes here
//        MongoCursor<Document> cursor = collection.find().iterator();
//        //Retrieving the documents
//        FindIterable<Document> iterDoc = collection.find();
//        Iterator it = iterDoc.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
    }
}
