package main.rest.question;

import main.rest.model.Word;

import java.util.Optional;

public interface QuestionRetriever {
    Optional<Word> getWord(String username, String language);
}
