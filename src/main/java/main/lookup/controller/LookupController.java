package main.lookup.controller;

import main.lookup.Lookup;
import main.lookup.data.Definitions;
import main.lookup.data.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

   @Autowired
   Lookup lookup;

    @GetMapping("/english/{englishWord}")
    @ResponseBody
    public ResponseEntity<Definitions> lookupEnglish(@PathVariable Word englishWord) {
        if (englishWord == null || englishWord.getWord() == null) {
            return ResponseEntity.badRequest().build();
        }
        return lookup.lookupIndonesianWord(englishWord)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/indonesian/{indonesianWord}")
    public ResponseEntity<Definitions> lookupIndonesian(@PathVariable Word indonesianWord) {
        if (indonesianWord == null || indonesianWord.getWord() == null) {
            return ResponseEntity.badRequest().build();
        }
        return lookup.lookupEnglishWord(indonesianWord)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
