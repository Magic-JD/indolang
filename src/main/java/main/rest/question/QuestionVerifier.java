package main.rest.question;

import main.rest.model.Answer;
import main.rest.model.Result;

public interface QuestionVerifier {
    Result verifyTest(String username, String language, Answer answer);
}
