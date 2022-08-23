package main.lookup;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class LookupController {

   @Autowired Lookup lookup;

    @GetMapping("/{englishWord}")
    public String lookupEnglish(@PathVariable String englishWord) {
        return lookup.lookupIndonesianWord(englishWord);
    }
}
