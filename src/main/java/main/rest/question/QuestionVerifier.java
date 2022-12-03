package main.rest.question;

import main.rest.question.data.Answer;
import main.rest.question.data.Result;

public interface QuestionVerifier {
    Result verifyTest(String username, Answer answer, String language);
}
