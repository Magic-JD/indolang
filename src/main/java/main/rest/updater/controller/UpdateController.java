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

    @Autowired DatabaseUpdater databaseUpdater;
    @Autowired LanguageValidator languageValidator;

    @PostMapping("/update/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWordToDictionary(@RequestBody Definition definition,
                                    @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        languageValidator.validateLanguage(language);
        databaseUpdater.updateDatabase(definition, language);
    }

    @PostMapping("/update/delete")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeWordFromDictionary(@RequestBody Definition definition,
                                         @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        languageValidator.validateLanguage(language);
        databaseUpdater.removeFromDatabase(definition, language);
    }
}
