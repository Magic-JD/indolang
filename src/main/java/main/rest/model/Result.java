package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class Result {
    private boolean pass;
    private String word;
    private String submittedTranslation;
    private Set<String> translations;
}
