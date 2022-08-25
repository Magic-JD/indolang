package main.lookup.controller;

import main.lookup.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

   @Autowired
   Lookup lookup;

    @GetMapping("/english/{englishWord}")
    @ResponseBody
    public String lookupEnglish(@PathVariable String englishWord) {
        return lookup.lookupIndonesianWord(englishWord);
    }

    @GetMapping("/indonesian/{indonesianWord}")
    @ResponseBody
    public String lookupIndonesian(@PathVariable String indonesianWord) {
        return lookup.lookupEnglishWord(indonesianWord);
    }
}
