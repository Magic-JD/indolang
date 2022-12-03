package main.lookup.controller;

import main.exception.Exceptions;
import main.lookup.Lookup;
import main.lookup.data.Definitions;
import main.lookup.data.Word;
import main.validation.LanguageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

    @Autowired Lookup lookup;
    @Autowired LanguageValidator languageValidator;

    @GetMapping("/{word}")
    @ResponseBody
    public ResponseEntity<Definitions> lookup(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable Word word) {
        languageValidator.validateLanguage(language);
        return lookup.lookupWord(language, word)
                .map(ResponseEntity::ok)
                .orElseThrow(Exceptions.WordNotFoundException::new);
    }
}
