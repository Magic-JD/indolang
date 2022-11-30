package main.test.controller;

import main.lookup.data.Word;
import main.test.TestRetriever;
import main.test.TestVerifier;
import main.test.data.Answer;
import main.test.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRetriever testRetriever;

    @Autowired
    TestVerifier testVerifier;

    @GetMapping("/english")
    @ResponseBody
    public ResponseEntity<Word> testWordEnglish() {
        return testRetriever.getEnglishWord()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/indonesian")
    @ResponseBody
    public ResponseEntity<Word> testWordIndonesian() {
        return testRetriever.getIndonesianWord()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/english")
    @ResponseBody
    public ResponseEntity<Result> validateWordEnglish(@RequestBody Answer answer) {
        return testVerifier.verifyTestEnglish(answer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/indonesian")
    @ResponseBody
    public ResponseEntity<Result> validateWordIndonesian(@RequestBody Answer answer) {
        return testVerifier.verifyTestIndonesian(answer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
