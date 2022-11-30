package main.dictionary.controller;

import main.dictionary.Dictionary;
import main.lookup.data.Definitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

   @Autowired
   Dictionary dictionary;

    @GetMapping("/english")
    public ResponseEntity<List<Definitions>> dictionaryEnglish() {
        return ResponseEntity.ok(dictionary.englishToIndonesian());
    }

    @GetMapping("/indonesian")
    public ResponseEntity<List<Definitions>> dictionaryIndonesian() {
        return ResponseEntity.ok(dictionary.indonesianToEnglish());
    }
}
