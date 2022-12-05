package main.rest.updater.controller;

import main.rest.model.Definition;
import main.rest.updater.DatabaseUpdater;
import main.validation.LanguageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateController {

    @Autowired private DatabaseUpdater databaseUpdater;
    @Autowired private LanguageValidator languageValidator;

    @PostMapping("/update/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWordToDictionary(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @RequestBody Definition definition) {
        languageValidator.validateLanguage(language);
        databaseUpdater.updateDatabase(language, definition);
    }

    @PostMapping("/update/delete")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeWordFromDictionary(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @RequestBody Definition definition) {
        languageValidator.validateLanguage(language);
        databaseUpdater.removeFromDatabase(language, definition);
    }
}
