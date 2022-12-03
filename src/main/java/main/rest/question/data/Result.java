package main.rest.question.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Result {
    boolean pass;
    String explanation;
}
