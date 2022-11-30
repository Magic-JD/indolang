package main;

import main.database.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
public class Application {

    @Autowired
    DictionaryRepository dicItemRep;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
