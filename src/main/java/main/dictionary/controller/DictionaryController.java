package main.dictionary.controller;

import main.dictionary.Dictionary;
import main.lookup.data.Definitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DictionaryController {

    @Autowired
    private Dictionary dictionary;

    @GetMapping("/dictionary")
    public ResponseEntity<List<Definitions>> dictionary(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return ResponseEntity.ok(dictionary.wordsToTranslations(language));
    }
}
