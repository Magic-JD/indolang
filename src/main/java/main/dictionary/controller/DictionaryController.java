package main.dictionary.controller;

import main.dictionary.Dictionary;
import main.lookup.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

   @Autowired
   Dictionary dictionary;

    @GetMapping("/english")
    public String dictionaryEnglish() {
        return dictionary.englishToIndonesian();
    }

    @GetMapping("/indonesian")
    public String dictionaryIndonesian() {
        return dictionary.indonesianToEnglish();
    }
}
