package main.question.controller;

import main.exception.Exceptions;
import main.lookup.data.Word;
import main.question.QuestionRetriever;
import main.question.QuestionVerifier;
import main.question.data.Answer;
import main.question.data.Result;
import main.validation.LanguageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    @Autowired QuestionRetriever questionRetriever;
    @Autowired QuestionVerifier questionVerifier;
    @Autowired LanguageValidator languageValidator;

    @GetMapping("/question")
    @ResponseBody
    public ResponseEntity<Word> testWord(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        languageValidator.validateLanguage(language);
        return questionRetriever.getWord(username, language)
                .map(ResponseEntity::ok)
                .orElseThrow(Exceptions.AllWordsLearnedException::new);
    }

    @PostMapping("/answer")
    @ResponseBody
    public ResponseEntity<Result> validateWord
            (@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
             @RequestBody Answer answer) {
        languageValidator.validateLanguage(language);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(questionVerifier.verifyTest(username, answer, language));
    }
}
