package main.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class Definitions {
    private String word;
    private Set<String> wordDefinitions;
}
