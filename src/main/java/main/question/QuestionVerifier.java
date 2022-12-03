package main.question;

import main.question.data.Answer;
import main.question.data.Result;

public interface QuestionVerifier {
    Result verifyTest(String username, Answer answer, String language);
}
