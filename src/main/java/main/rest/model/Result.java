package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Result {
    private boolean pass;
    private String word;
    private String submittedTranslation;
    private Set<String> correctTranslations;

    public static Result createPassingResult(Answer answer) {
        return new Result(true, answer.getAskedQuestion(), answer.getAnswer(), Collections.emptySet());
    }

    public static Result createFailingResult(Answer answer, Set<String> correctTranslations) {
        return new Result(false, answer.getAskedQuestion(), answer.getAnswer(), correctTranslations);
    }
}
