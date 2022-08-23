package main.lookup.controller;

import main.lookup.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lookup")
public class LookupController {

   @Autowired
   Lookup lookup;

    @GetMapping("/english/{englishWord}")
    public String lookupEnglish(@PathVariable String englishWord) {
        return lookup.lookupIndonesianWord(englishWord);
    }

    @GetMapping("/indonesian/{indonesianWord}")
    public String lookupIndonesian(@PathVariable String indonesianWord) {
        return lookup.lookupEnglishWord(indonesianWord);
    }
}
