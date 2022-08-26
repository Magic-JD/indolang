package main.test.controller;

import main.test.TestRetriever;
import main.test.TestVerifier;
import main.test.data.Answer;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String testWordEnglish() {
        return testRetriever.getEnglishWord();
    }

    @GetMapping("/indonesian")
    @ResponseBody
    public String testWordIndonesian() {
        return testRetriever.getIndonesianWord();
    }

    @PostMapping("/english")
    @ResponseBody
    public String validateWordEnglish(@RequestBody Answer answer) {
        return testVerifier.verifyTestEnglish(answer);
    }

    @PostMapping("/indonesian")
    @ResponseBody
    public String validateWordIndonesian(@RequestBody Answer answer) {
        return testVerifier.verifyTestIndonesian(answer);
    }
}
