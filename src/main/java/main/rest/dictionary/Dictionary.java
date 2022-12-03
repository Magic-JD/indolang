package main.rest.dictionary;

import main.rest.model.Definitions;

import java.util.List;

public interface Dictionary {
    List<Definitions> wordsToTranslations(String language);
}
