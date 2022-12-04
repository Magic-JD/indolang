package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {
    private String askedQuestion;
    private String answer;
}
