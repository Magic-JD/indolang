package main.test.controller;

import main.test.TestRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRetriever testRetriever;

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


}
