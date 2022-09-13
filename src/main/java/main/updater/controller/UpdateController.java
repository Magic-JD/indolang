package main.updater.controller;

import main.updater.UpdateFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
