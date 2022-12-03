package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class Result {
    boolean pass;
    String word;
    String submittedTranslation;
    Set<String> translations;
}
