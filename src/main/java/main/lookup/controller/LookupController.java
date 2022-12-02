package main.lookup.controller;

import main.lookup.Lookup;
import main.lookup.data.Definitions;
import main.lookup.data.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

   @Autowired
   Lookup lookup;

    @GetMapping("/{word}")
    @ResponseBody
    public ResponseEntity<Definitions> lookup(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable Word word) {
        if (word == null || word.getWord() == null) {
            return ResponseEntity.badRequest().build();
        }
        return lookup.lookupWord(language, word)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
