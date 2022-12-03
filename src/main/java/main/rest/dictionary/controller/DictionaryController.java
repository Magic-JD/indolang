package main.rest.dictionary.controller;

import lombok.extern.slf4j.Slf4j;
import main.rest.dictionary.Dictionary;
import main.rest.model.Definitions;
import main.validation.LanguageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
public class DictionaryController {

    @Autowired private Dictionary dictionary;
    @Autowired private LanguageValidator languageValidator;

    //TODO paginate or remove
    @GetMapping("/dictionary")
    public ResponseEntity<List<Definitions>> dictionary(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        languageValidator.validateLanguage(language);
        return ResponseEntity.ok(dictionary.wordsToTranslations(language));
    }
}
