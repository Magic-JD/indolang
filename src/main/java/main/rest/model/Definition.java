package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Definition {
    private final String word;
    private final String translation;
}
