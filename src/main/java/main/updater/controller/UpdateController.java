package main.updater.controller;

import main.updater.DatabaseUpdater;
import main.updater.data.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateController {

    @Autowired DatabaseUpdater databaseUpdater;

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWordToDictionary(@RequestBody Definition definition,
                                    @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        databaseUpdater.updateDatabase(definition, language);
    }

}
