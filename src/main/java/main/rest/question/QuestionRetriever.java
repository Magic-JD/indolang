package main.rest.question;

import main.rest.model.Word;

public interface QuestionRetriever {
    Word getWord(String username, String language);
}
