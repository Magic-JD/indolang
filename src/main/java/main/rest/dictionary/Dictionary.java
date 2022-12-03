package main.rest.dictionary;

import main.rest.lookup.data.Definitions;

import java.util.List;

public interface Dictionary {
    List<Definitions> wordsToTranslations(String language);
}
