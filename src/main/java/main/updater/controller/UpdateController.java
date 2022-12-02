package main.updater.controller;

import main.updater.DatabaseUpdater;
import main.updater.data.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    DatabaseUpdater databaseUpdater;

    @PostMapping("/dictionary")
    public ResponseEntity<Boolean> addWordToDictionary(@RequestBody Definition definition,
                                                       @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(databaseUpdater.updateDatabase(definition, language));
    }

}
