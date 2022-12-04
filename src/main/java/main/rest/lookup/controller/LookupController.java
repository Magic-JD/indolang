package main.rest.lookup.controller;

import main.exception.Exceptions;
import main.rest.lookup.Lookup;
import main.rest.model.Definitions;
import main.rest.model.Word;
import main.validation.LanguageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

    @Autowired private Lookup lookup;
    @Autowired private LanguageValidator languageValidator;

    @GetMapping("/{word}")
    @ResponseBody
    public ResponseEntity<Definitions> lookup(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable Word word) {
        languageValidator.validateLanguage(language);
        return lookup.lookupWord(language, word)
                .map(ResponseEntity::ok)
                .orElseThrow(Exceptions.WordNotFoundException::new);
    }
}
