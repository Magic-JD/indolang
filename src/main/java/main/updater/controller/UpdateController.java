package main.updater.controller;

import main.updater.UpdateFile;
import main.updater.data.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.util.Date;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    UpdateFile updateFile;

    @Scheduled(fixedRate = 1000000)
    @PreDestroy
    @PutMapping("")
    public void updateFromFile() {
        updateFile.updateFileFromMemoryData();
        System.out.println("Updated file at: " + new Date());
    }

    @PutMapping("/dictionary")
    public void updateFromDictionary() {
        updateFile.updateFromDictionaryFile();
    }


    @PostMapping("/user")
    public ResponseEntity<Boolean> addWordToDictionary(@RequestBody Definition definition) {
        return updateFile.addWordToDictionary(definition) ?
                ResponseEntity.ok(true) :
                ResponseEntity.unprocessableEntity().build();
    }

}
