package main.question.controller;

import main.lookup.data.Word;
import main.question.QuestionRetriever;
import main.question.QuestionVerifier;
import main.question.data.Answer;
import main.question.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    @Autowired
    QuestionRetriever questionRetriever;

    @Autowired
    QuestionVerifier questionVerifier;

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<Word> testWord(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return questionRetriever.getWord(username, language)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<Result> validateWord
            (@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
             @RequestBody Answer answer) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //TODO sort out error handling here.
        return ResponseEntity.ok(questionVerifier.verifyTest(username, answer, language));
    }
}
