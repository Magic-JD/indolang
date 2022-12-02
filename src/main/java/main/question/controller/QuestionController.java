package main.question.controller;

import main.lookup.data.Word;
import main.question.QuestionRetriever;
import main.question.QuestionVerifier;
import main.question.data.Answer;
import main.question.data.Result;
import main.registration.data.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    @Autowired
    QuestionRetriever questionRetriever;

    @Autowired
    QuestionVerifier questionVerifier;

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<Word> testWord(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @RequestBody UserCredentialsDto userCredentialsDto) {
        return questionRetriever.getWord(language, userCredentialsDto.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<Result> validateWord
            (@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
             @RequestBody UserCredentialsDto userCredentialsDto,
             @RequestBody Answer answer) {
        //TODO sort out error handling here.
        return ResponseEntity.ok(questionVerifier.verifyTest(userCredentialsDto.getUsername(), answer, language));
    }
}
