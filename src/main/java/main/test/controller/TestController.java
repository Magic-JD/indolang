package main.test.controller;

import main.lookup.data.Word;
import main.registration.data.UserCredentialsDto;
import main.test.TestRetriever;
import main.test.TestVerifier;
import main.test.data.Answer;
import main.test.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Word> testWord(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @RequestBody UserCredentialsDto userCredentialsDto) {
        return testRetriever.getWord(language, userCredentialsDto.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Result> validateWord
            (@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
             @RequestBody UserCredentialsDto userCredentialsDto,
             @RequestBody Answer answer) {
        //TODO sort out error handling here.
        return ResponseEntity.ok(testVerifier.verifyTest(userCredentialsDto.getUsername(), answer, language));
    }
}
