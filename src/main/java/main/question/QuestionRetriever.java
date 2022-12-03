package main.question;

import main.lookup.data.Word;

import java.util.Optional;

public interface QuestionRetriever {
    Optional<Word> getWord(String username, String language);
}
